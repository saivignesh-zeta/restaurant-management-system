package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.BookingDAO;
import com.restaurant.management.model.Booking;
import com.restaurant.management.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {



    @Override
    public void saveBooking(Booking booking) {
        String query = "INSERT INTO bookings (customer_id, table_id, booking_time, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getTableId());
            stmt.setTimestamp(3, Timestamp.valueOf(booking.getBookingTime()));
            stmt.setString(4, booking.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save booking", e);
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        String query = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch booking by ID", e);
        }
        return null;
    }

    @Override
    public List<Booking> getBookingsByCustomer(int customerId) {
        String query = "SELECT * FROM bookings WHERE customer_id = ?";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch bookings by customer", e);
        }

        return bookings;
    }

    @Override
    public boolean isTableAvailable(int tableId, LocalDateTime time) {
        String query = "SELECT COUNT(*) FROM bookings WHERE table_id = ? AND booking_time = ? AND status = 'Confirmed'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, tableId);
            stmt.setTimestamp(2, Timestamp.valueOf(time));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to check table availability", e);
        }

        return false;
    }

    @Override
    public void cancelBooking(int bookingId) {
        String query = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to cancel booking", e);
        }
    }

    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("booking_id"),
                rs.getInt("customer_id"),
                rs.getInt("table_id"),
                rs.getTimestamp("booking_time").toLocalDateTime(),
                rs.getString("status")
        );
    }
}
