package com.restaurant.management.dao;

import com.restaurant.management.model.Order;

import java.util.List;

public interface OrderDAO {

    void saveOrder(Order order);
    Order getOrderById(int orderId);
    List<Order> getOrdersByTable(int tableId);
    void updateOrderStatus(int orderId, String status);
    List<Order> getPendingOrders();

}
