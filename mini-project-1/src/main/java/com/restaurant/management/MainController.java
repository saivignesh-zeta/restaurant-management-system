package com.restaurant.management;

import com.restaurant.management.controller.*;
import com.restaurant.management.dao.*;
import com.restaurant.management.dao.impl.*;
import com.restaurant.management.service.impl.*;
import com.restaurant.management.util.DBConnection;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Instantiate DAO implementations
        BookingDAO bookingDAO = new BookingDAOImpl();
        RestaurantTableDAO tableDAO = new RestaurantTableDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
        BillDAO billDAO = new BillDAOImpl(DBConnection.getConnection());
        MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

// 2. Instantiate services using DAOs
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAO, tableDAO);
        OrderServiceImpl orderService = new OrderServiceImpl(orderDAO, orderItemDAO);
        BillingServiceImpl billingService = new BillingServiceImpl(billDAO, orderItemDAO);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl(); // uses MenuItemDAOImpl internally

// 3. Instantiate controllers using services + DAOs
        BookingController bookingController = new BookingController(bookingService);
        WaiterOrderController waiterOrderController = new WaiterOrderController(orderService, menuItemDAO);
        KitchenOrderController kitchenOrderController = new KitchenOrderController(orderService);
        BillingController billingController = new BillingController(billingService, orderService);
        AdminController adminController = new AdminController(menuItemService, billingService);

        while (true) {
            System.out.println("\n========================================");
            System.out.println("  Welcome to Restaurant Management System");
            System.out.println("========================================");
            System.out.println("Choose your role:");
            System.out.println("1. Customer - Book Table");
            System.out.println("2. Waiter   - Place/View Orders");
            System.out.println("3. Kitchen  - View/Update Kitchen Orders");
            System.out.println("4. Manager  - Billing & Payments");
            System.out.println("5. Admin    - Manage Menu & Reports");
            System.out.println("6. Exit System");

            System.out.print("\nEnter choice: ");
            int choice = -1;

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear bad input
                System.out.println(" Invalid input. Please enter a number from 1 to 6.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\n Customer Portal");
                    bookingController.showBookingMenu();
                }
                case 2 -> {
                    System.out.println("\n Waiter Portal");
                    waiterOrderController.showWaiterOrderMenu();
                }
                case 3 -> {
                    System.out.println("\n Kitchen Staff Portal");
                    kitchenOrderController.showKitchenOrderMenu();
                }
                case 4 -> {
                    System.out.println("\n Billing Manager Portal");
                    billingController.showBillingMenu();
                }
                case 5 -> {
                    System.out.println("\n Admin Portal");
                    adminController.showAdminMenu();
                }
                case 6 -> {
                    System.out.println("\n Exiting system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println(" Invalid option. Please try again.");
            }
        }
    }
}
