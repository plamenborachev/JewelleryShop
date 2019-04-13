package org.softuni.jewelleryshop.service;

import org.softuni.jewelleryshop.domain.models.service.OrderServiceModel;
import org.softuni.jewelleryshop.domain.models.view.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    BigDecimal calcTotal(List<ShoppingCartItem> cart);

    OrderServiceModel prepareOrder(List<ShoppingCartItem> cart, String customer);

    void createOrder(OrderServiceModel orderServiceModel);

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrdersByCustomer(String username);

    OrderServiceModel findOrderById(String id);
}
