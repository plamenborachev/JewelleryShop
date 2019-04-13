package org.softuni.jewelleryshop.service;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.entities.Order;
import org.softuni.jewelleryshop.domain.models.service.OrderProductServiceModel;
import org.softuni.jewelleryshop.domain.models.service.OrderServiceModel;
import org.softuni.jewelleryshop.domain.models.service.ProductServiceModel;
import org.softuni.jewelleryshop.domain.models.view.ShoppingCartItem;
import org.softuni.jewelleryshop.error.OrderNotFoundException;
import org.softuni.jewelleryshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductService productService, UserService userService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public BigDecimal calcTotal(List<ShoppingCartItem> cart) {
        BigDecimal result = new BigDecimal(0);
        for (ShoppingCartItem item : cart) {
            result = result.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return result;
    }

    @Override
    public OrderServiceModel prepareOrder(List<ShoppingCartItem> cart, String customer) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setCustomer(this.userService.findUserByUserName(customer));
        List<OrderProductServiceModel> products = new ArrayList<>();
        for (ShoppingCartItem item : cart) {
            OrderProductServiceModel orderProductServiceModel
                    = this.modelMapper.map(item.getProduct(), OrderProductServiceModel.class);
            String productId = orderProductServiceModel.getProduct().getId();
            ProductServiceModel productServiceModel = this.productService.findProductById(productId);
            int productQuantity = productServiceModel.getQuantity();
            if (item.getQuantity() > productQuantity) {
                throw new IllegalArgumentException(String.format(GlobalConstants.PRODUCT_LIMITED_QUANTITY,
                        productQuantity, item.getProduct().getProduct().getName()));
            }
        }
        for (ShoppingCartItem item : cart) {
            OrderProductServiceModel orderProductServiceModel
                    = this.modelMapper.map(item.getProduct(), OrderProductServiceModel.class);
            String productId = orderProductServiceModel.getProduct().getId();
            ProductServiceModel productServiceModel = this.productService.findProductById(productId);
            this.productService.decreaseProductQuantity(productId, item.getQuantity(), productServiceModel);
            products.add(orderProductServiceModel);
        }
        orderServiceModel.setProducts(products);
        orderServiceModel.setTotalPrice(this.calcTotal(cart));
        return orderServiceModel;
    }

    @Override
    @Transactional
    public void createOrder(OrderServiceModel orderServiceModel) {
        orderServiceModel.setFinishedOn(LocalDateTime.now());
        this.orderRepository.saveAndFlush(this.modelMapper.map(orderServiceModel, Order.class));
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        List<Order> orders = this.orderRepository.findAll();
        return orders
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findOrdersByCustomer(String username) {
        return this.orderRepository.findAllOrdersByCustomer_UsernameOrderByFinishedOn(username)
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel findOrderById(String id) {
        return this.orderRepository.findById(id)
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .orElseThrow(() -> new OrderNotFoundException(GlobalConstants.ORDER_NOT_FOUND_EXCEPTION_MESSAGE));
    }
}
