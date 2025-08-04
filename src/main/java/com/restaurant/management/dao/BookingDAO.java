package com.restaurant.management.dao;

import com.restaurant.management.model.Booking;

import java.util.List;

public interface BookingDAO {

    void saveBooking(Booking booking);
    Booking getBookingById(int bookingId);
    List<Booking> getBookingsByCustomer(int customerId);
    boolean isTableAvailable(int tableId, java.time.LocalDateTime time);
    void cancelBooking(int bookingId);

}
