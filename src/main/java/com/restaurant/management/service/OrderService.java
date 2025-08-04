package com.restaurant.management.service;

import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;

import java.util.List;

public interface OrderService {
    int placeOrder(int tableId, int waiterId, List<OrderItem> items);
    List<Order> getOrdersByTable(int tableId);
    void updateOrderStatus(int orderId, String status);
    List<Order> getPendingOrders();
}
