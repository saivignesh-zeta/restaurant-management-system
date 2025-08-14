package com.restaurant.management.service.impl;

import com.restaurant.management.dao.BookingDAO;
import com.restaurant.management.dao.RestaurantTableDAO;
import com.restaurant.management.model.Booking;
import com.restaurant.management.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final RestaurantTableDAO tableDAO;

    public BookingServiceImpl(BookingDAO bookingDAO, RestaurantTableDAO tableDAO) {
        this.bookingDAO = bookingDAO;
        this.tableDAO = tableDAO;
    }

    @Override
    public boolean bookTable(int customerId, int tableId, LocalDateTime time) {
        if (!isTableAvailable(tableId, time)) {
            return false;
        }

        Booking booking = new Booking();
        booking.setCustomerId(customerId);
        booking.setTableId(tableId);
        booking.setBookingTime(time);
        booking.setStatus("Confirmed");

        bookingDAO.saveBooking(booking);
        tableDAO.updateTableStatus(tableId, "Booked");

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(int customerId) {
        return bookingDAO.getBookingsByCustomer(customerId);
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null || !"Confirmed".equalsIgnoreCase(booking.getStatus())) {
            return false;
        }

        bookingDAO.cancelBooking(bookingId);
        tableDAO.updateTableStatus(booking.getTableId(), "Available");
        return true;
    }

    @Override
    public boolean isTableAvailable(int tableId, LocalDateTime time) {
        return bookingDAO.isTableAvailable(tableId, time);
    }
}
