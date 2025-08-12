package com.restaurant.management.controller;

import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.BillingService;
import com.restaurant.management.service.OrderService;

import java.util.List;
import java.util.Scanner;

public class BillingController {

    private final BillingService billingService;
    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    public BillingController(BillingService billingService, OrderService orderService) {
        this.billingService = billingService;
        this.orderService = orderService;
    }

    public void showBillingMenu() {
        while (true) {
            System.out.println("\n--- Billing Menu ---");
            System.out.println("1. Generate Bill for Table");
            System.out.println("2. Record Payment");
            System.out.println("3. View Paid Bills");
            System.out.println("4. Back");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> generateBill();
                case 2 -> recordPayment();
                case 3 -> viewPaidBills();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void generateBill() {
        System.out.print("Enter Table ID: ");
        int tableId = scanner.nextInt();

        List<Order> orders = orderService.getOrdersByTable(tableId);
        List<Order> unpaidOrders = orders.stream()
                .filter(order -> !billingService.isOrderBilled(order.getOrderId()))
                .toList();

        if (unpaidOrders.isEmpty()) {
            System.out.println(" No unpaid orders for this table.");
            return;
        }

        double total = billingService.calculateTotalAmount(unpaidOrders);
        int billId = billingService.generateBill(unpaidOrders, total);

        System.out.printf(" Bill generated (Bill ID: %d). Total Amount: ₹%.2f%n", billId, total);
    }

    private void recordPayment() {
        System.out.print("Enter Bill ID: ");
        int billId = scanner.nextInt();

        System.out.print("Enter Payment Method (Cash / Card / UPI): ");
        scanner.nextLine(); // consume newline
        String method = scanner.nextLine();

        boolean success = billingService.markBillAsPaid(billId, method);
        System.out.println(success ? " Payment recorded." : " Payment failed. Check bill ID.");
    }

    private void viewPaidBills() {
        billingService.getAllPaidBills().forEach(bill -> {
            System.out.printf("Bill ID: %d | Amount: ₹%.2f | Paid via: %s | Time: %s%n",
                    bill.getBillId(), bill.getTotalAmount(), bill.getPaymentMethod(), bill.getPaidTime());
        });
    }
}
