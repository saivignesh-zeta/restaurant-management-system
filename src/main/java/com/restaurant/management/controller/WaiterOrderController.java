package com.restaurant.management.controller;

import com.restaurant.management.dao.MenuItemDAO;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WaiterOrderController {

    private final OrderService orderService;
    private final MenuItemDAO menuItemDAO;
    private final Scanner scanner = new Scanner(System.in);

    public WaiterOrderController(OrderService orderService, MenuItemDAO menuItemDAO) {
        this.orderService = orderService;
        this.menuItemDAO = menuItemDAO;
    }

    public void showWaiterOrderMenu() {
        while (true) {
            System.out.println("\n--- Waiter Order Menu ---");
            System.out.println("1. Place New Order");
            System.out.println("2. View Orders by Table");
            System.out.println("3. Mark Order as Served");
            System.out.println("4. Back");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> placeOrder();
                case 2 -> viewOrdersByTable();
                case 3 -> markAsServed();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void placeOrder() {
        System.out.print("Enter Waiter ID: ");
        int waiterId = scanner.nextInt();

        System.out.print("Enter Table ID: ");
        int tableId = scanner.nextInt();

        System.out.println("--- Menu Items ---");
        List<MenuItem> menu = menuItemDAO.getAllItems();
        for (MenuItem item : menu) {
            System.out.printf("ID: %d | Name: %s | Price: %.2f | Category: %s%n",
                    item.getItemId(), item.getName(), item.getPrice(), item.getCategory());
        }

        List<OrderItem> items = new ArrayList<>();
        while (true) {
            System.out.print("Enter Item ID to add to order (0 to finish): ");
            int itemId = scanner.nextInt();
            if (itemId == 0) break;

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();

            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(itemId);
            orderItem.setQuantity(quantity);
            items.add(orderItem);
        }

        if (items.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        int orderId = orderService.placeOrder(tableId, waiterId, items);
        System.out.println("Order placed successfully. Order ID: " + orderId);
    }

    private void viewOrdersByTable() {
        System.out.print("Enter Table ID: ");
        int tableId = scanner.nextInt();

        List<Order> orders = orderService.getOrdersByTable(tableId);
        if (orders.isEmpty()) {
            System.out.println("No orders found for this table.");
        } else {
            for (Order order : orders) {
                System.out.printf("Order ID: %d | Waiter ID: %d | Time: %s | Status: %s%n",
                        order.getOrderId(), order.getWaiterId(), order.getOrderTime(), order.getStatus());
            }
        }
    }

    private void markAsServed() {
        System.out.print("Enter Order ID to mark as Served: ");
        int orderId = scanner.nextInt();
        orderService.updateOrderStatus(orderId, "Served");
        System.out.println(" Order marked as Served.");
    }
}
