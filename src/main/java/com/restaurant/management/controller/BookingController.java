package com.restaurant.management.controller;

import com.restaurant.management.dao.RestaurantTableDAO;
import com.restaurant.management.dao.impl.RestaurantTableDAOImpl;
import com.restaurant.management.model.Booking;
import com.restaurant.management.model.RestaurantTable;
import com.restaurant.management.service.BookingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingController {

    private final BookingService bookingService;
    private final RestaurantTableDAO tableDAO;
    private final Scanner scanner = new Scanner(System.in);

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
        this.tableDAO = new RestaurantTableDAOImpl();
    }

    public void showBookingMenu() {
        while (true) {
            System.out.println("\n--- Booking Menu ---");
            System.out.println("1. Book a Table");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. Back");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> bookTable();
                case 2 -> viewBookings();
                case 3 -> cancelBooking();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void bookTable() {
        System.out.print("Enter your Customer ID: ");
        int customerId = scanner.nextInt();

        LocalDateTime time;
        List<RestaurantTable> availableTables;

        while (true) {
            System.out.print("Enter Booking Time (YYYY-MM-DDTHH:MM) or type 'exit' to cancel: ");
            String timeInput = scanner.next();

            if (timeInput.equalsIgnoreCase("exit")) {
                System.out.println("Booking cancelled.");
                return;
            }

            try {
                time = LocalDateTime.parse(timeInput);
            } catch (Exception e) {
                System.out.println(" Invalid time format. Please try again.");
                continue;
            }

            availableTables = tableDAO.getAvailableTablesByTime(time);

            if (!availableTables.isEmpty()) {
                break; // exit loop — available tables found
            } else {
                System.out.println(" No tables available at that time. Please try a different time.");
            }
        }

        System.out.println(" Available Tables:");
        for (RestaurantTable table : availableTables) {
            System.out.printf("Table ID: %d | Capacity: %d | Status: %s%n",
                    table.getTableId(), table.getCapacity(), table.getStatus());
        }

        System.out.print("Enter Table ID to book from the list: ");
        int selectedTableId = scanner.nextInt();

        boolean success = bookingService.bookTable(customerId, selectedTableId, time);
        System.out.println(success ? " Table booked successfully." : " Booking failed.");
    }

    private void viewBookings() {
        System.out.print("Enter your Customer ID: ");
        int customerId = scanner.nextInt();

        List<Booking> bookings = bookingService.getBookingsByCustomer(customerId);
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("--- Your Bookings ---");
            for (Booking booking : bookings) {
                System.out.printf("Booking ID: %d | Table: %d | Time: %s | Status: %s%n",
                        booking.getBookingId(),
                        booking.getTableId(),
                        booking.getBookingTime(),
                        booking.getStatus());
            }
        }
    }

    private void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();

        boolean result = bookingService.cancelBooking(bookingId);
        System.out.println(result ? " Booking cancelled." : " Invalid booking ID or already cancelled.");
    }
}
