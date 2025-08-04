package com.restaurant.management.controller;

import com.restaurant.management.model.Bill;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.service.BillingService;
import com.restaurant.management.service.MenuItemService;

import java.util.List;
import java.util.Scanner;

public class AdminController {
    private final MenuItemService menuItemService;
    private final BillingService billingService;
    private final Scanner scanner;

    public AdminController(MenuItemService menuItemService, BillingService billingService) {
        this.menuItemService = menuItemService;
        this.billingService = billingService;
        this.scanner = new Scanner(System.in);
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n Admin Dashboard:");
            System.out.println("1. View Menu");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Update Menu Item");
            System.out.println("4. Delete Menu Item");
            System.out.println("5. View All Paid Bills");
            System.out.println("6. Exit Admin Panel");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewMenu();
                case 2 -> addMenuItem();
                case 3 -> updateMenuItem();
                case 4 -> deleteMenuItem();
                case 5 -> viewPaidBills();
                case 6 -> {
                    System.out.println(" Exiting Admin Panel...");
                    return;
                }
                default -> System.out.println(" Invalid choice. Please try again.");
            }
        }
    }

    private void viewMenu() {
        List<MenuItem> items = menuItemService.getAllItems();
        System.out.println("\n Menu:");
        for (MenuItem item : items) {
            System.out.printf("ID: %d | Name: %s | Price: %.2f | Category: %s%n",
                    item.getItemId(), item.getName(), item.getPrice(), item.getCategory());
        }
    }

    private void addMenuItem() {
        System.out.println("\n➕ Add Menu Item:");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        MenuItem newItem = new MenuItem();
        newItem.setName(name);
        newItem.setPrice(price);
        newItem.setCategory(category);

        if (menuItemService.addMenuItem(newItem)) {
            System.out.println(" Item added successfully!");
        } else {
            System.out.println(" Failed to add item.");
        }
    }

    private void updateMenuItem() {
        System.out.println("\n✏ Update Menu Item:");
        System.out.print("Enter item ID to update: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();

        MenuItem existing = menuItemService.getItemById(itemId);
        if (existing == null) {
            System.out.println(" Item not found.");
            return;
        }

        System.out.print("New name (" + existing.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) existing.setName(name);

        System.out.print("New price (" + existing.getPrice() + "): ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isBlank()) existing.setPrice(Double.parseDouble(priceStr));

        System.out.print("New category (" + existing.getCategory() + "): ");
        String category = scanner.nextLine();
        if (!category.isBlank()) existing.setCategory(category);

        if (menuItemService.updateMenuItem(existing)) {
            System.out.println(" Item updated.");
        } else {
            System.out.println(" Update failed.");
        }
    }

    private void deleteMenuItem() {
        System.out.println("\n Delete Menu Item:");
        System.out.print("Enter item ID to delete: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();

        if (menuItemService.deleteMenuItem(itemId)) {
            System.out.println(" Item deleted.");
        } else {
            System.out.println(" Deletion failed.");
        }
    }


    private void viewPaidBills() {
        System.out.println("\n Paid Bills Report:");
        List<Bill> paidBills = billingService.getAllPaidBills();
        double total = 0;
        for (Bill bill : paidBills) {
            total += bill.getTotalAmount();
            System.out.printf("Bill ID: %d | Amount: %.2f | Paid on: %s | Method: %s%n",
                    bill.getBillId(), bill.getTotalAmount(), bill.getPaidTime(), bill.getPaymentMethod());
        }
        System.out.printf(" Total Revenue: %.2f%n", total);
    }
}
