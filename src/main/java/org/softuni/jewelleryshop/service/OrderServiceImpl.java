package org.softuni.jewelleryshop.service;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.entities.Order;
import org.softuni.jewelleryshop.domain.models.service.OrderServiceModel;
import org.softuni.jewelleryshop.error.OrderNotFoundException;
import org.softuni.jewelleryshop.repository.OrderRepository;
import org.softuni.jewelleryshop.repository.ProductRepository;
import org.softuni.jewelleryshop.validation.ProductValidationService;
import org.softuni.jewelleryshop.validation.UserValidationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserValidationService userValidation;
    private final ProductValidationService productValidation;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserService userService,
            UserValidationService userValidation,
            ProductValidationService productValidation,
            ModelMapper modelMapper
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.userValidation = userValidation;
        this.productValidation = productValidation;
        this.modelMapper = modelMapper;
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
        List<OrderServiceModel> orderServiceModels = orders
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());

        return orderServiceModels;
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
