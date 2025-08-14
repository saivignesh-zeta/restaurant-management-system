package com.restaurant.management.service.impl;

import com.restaurant.management.dao.MenuItemDAO;
import com.restaurant.management.dao.OrderDAO;
import com.restaurant.management.dao.OrderItemDAO;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.ReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ReportServiceImpl implements ReportService {

    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final MenuItemDAO menuItemDAO;

    public ReportServiceImpl(OrderDAO orderDAO, OrderItemDAO orderItemDAO, MenuItemDAO menuItemDAO) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.menuItemDAO = menuItemDAO;
    }

    @Override
    public double getTotalSalesByDate(LocalDate date) {
        List<Order> orders = getOrdersOnDate(date);
        double total = 0.0;

        for (Order order : orders) {
            List<OrderItem> items = orderItemDAO.getItemsByOrderId(order.getOrderId());
            for (OrderItem item : items) {
                MenuItem menuItem = menuItemDAO.getItemById(item.getItemId());
                total += item.getQuantity() * menuItem.getPrice();
            }
        }

        return total;
    }

    @Override
    public Map<String, Double> getSalesByCategory(LocalDate date) {
        List<Order> orders = getOrdersOnDate(date);
        Map<String, Double> categorySales = new HashMap<>();

        for (Order order : orders) {
            List<OrderItem> items = orderItemDAO.getItemsByOrderId(order.getOrderId());
            for (OrderItem item : items) {
                MenuItem menuItem = menuItemDAO.getItemById(item.getItemId());
                double itemTotal = item.getQuantity() * menuItem.getPrice();

                categorySales.merge(menuItem.getCategory(), itemTotal, Double::sum);
            }
        }

        return categorySales;
    }

    @Override
    public int getTotalOrdersByDate(LocalDate date) {
        return getOrdersOnDate(date).size();
    }

    private List<Order> getOrdersOnDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<Order> allOrders = orderDAO.getPendingOrders(); // or fetch all (add custom method)
        List<Order> filtered = new ArrayList<>();

        for (Order order : allOrders) {
            if (!order.getOrderTime().isBefore(start) && !order.getOrderTime().isAfter(end)) {
                filtered.add(order);
            }
        }

        return filtered;
    }
}
