package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.RestaurantTableDAO;
import com.restaurant.management.model.RestaurantTable;
import com.restaurant.management.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTableDAOImpl implements RestaurantTableDAO {
    @Override
    public List<RestaurantTable> getAllTables() {
        String query = "SELECT * FROM restaurant_tables";
        List<RestaurantTable> tables = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tables.add(mapResultSetToTable(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all tables", e);
        }

        return tables;
    }

    @Override
    public RestaurantTable getTableById(int tableId) {
        String query = "SELECT * FROM restaurant_tables WHERE table_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, tableId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTable(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch table by ID", e);
        }

        return null;
    }

    @Override
    public void updateTableStatus(int tableId, String status) {
        String sql = "UPDATE restaurant_tables SET status = ? WHERE table_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status); // Available / Booked / Occupied
            stmt.setInt(2, tableId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update table status", e);
        }
    }

    @Override
    public List<RestaurantTable> getAvailableTablesByTime(LocalDateTime time) {
        String query = """
                 SELECT * FROM restaurant_tables rt
                   WHERE LOWER(rt.status) = 'available' AND rt.table_id NOT IN (
                       SELECT b.table_id FROM bookings b
                       WHERE b.booking_time = ? AND LOWER(b.status) = 'confirmed'
                   )
        """;

        List<RestaurantTable> available = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(time));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                available.add(mapResultSetToTable(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch available tables", e);
        }

        return available;
    }

    private RestaurantTable mapResultSetToTable(ResultSet rs) throws SQLException {
        return new RestaurantTable(
                rs.getInt("table_id"),
                rs.getInt("capacity"),
                rs.getString("status")
        );
    }
}
