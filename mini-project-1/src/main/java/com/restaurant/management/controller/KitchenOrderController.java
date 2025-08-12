package com.restaurant.management.controller;

import com.restaurant.management.model.Order;
import com.restaurant.management.service.OrderService;

import java.util.List;
import java.util.Scanner;

public class KitchenOrderController {

    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    public KitchenOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void showKitchenOrderMenu() {
        while (true) {
            System.out.println("\n--- Kitchen Order Menu ---");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Mark Order as Prepared");
            System.out.println("3. Back");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> viewPendingOrders();
                case 2 -> markAsPrepared();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewPendingOrders() {
        List<Order> orders = orderService.getPendingOrders();
        if (orders.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            for (Order order : orders) {
                System.out.printf("Order ID: %d | Table ID: %d | Waiter ID: %d | Time: %s%n",
                        order.getOrderId(), order.getTableId(), order.getWaiterId(), order.getOrderTime());
            }
        }
    }

    private void markAsPrepared() {
        System.out.print("Enter Order ID to mark as Prepared: ");
        int orderId = scanner.nextInt();
        orderService.updateOrderStatus(orderId, "Prepared");
        System.out.println(" Order marked as Prepared.");
    }
}
