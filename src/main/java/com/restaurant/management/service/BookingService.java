package com.restaurant.management.service;

import com.restaurant.management.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    boolean bookTable(int customerId, int tableId, LocalDateTime time);
    List<Booking> getBookingsByCustomer(int customerId);
    boolean cancelBooking(int bookingId);
    boolean isTableAvailable(int tableId, LocalDateTime time);
}
