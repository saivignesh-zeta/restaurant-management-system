package com.restaurant.management.service.impl;

import com.restaurant.management.dao.OrderDAO;
import com.restaurant.management.dao.OrderItemDAO;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;

    public OrderServiceImpl(OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    public int placeOrder(int tableId, int waiterId, List<OrderItem> items) {
        Order order = new Order();
        order.setTableId(tableId);
        order.setWaiterId(waiterId);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("Pending");

        orderDAO.saveOrder(order); // order_id will be set after insertion

        for (OrderItem item : items) {
            item.setOrderId(order.getOrderId());
            orderItemDAO.saveOrderItem(item);
        }

        return order.getOrderId();
    }

    @Override
    public List<Order> getOrdersByTable(int tableId) {
        return orderDAO.getOrdersByTable(tableId);
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        orderDAO.updateOrderStatus(orderId, status);
    }

    @Override
    public List<Order> getPendingOrders() {
        return orderDAO.getPendingOrders();
    }
}
