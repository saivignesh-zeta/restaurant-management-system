package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.OrderDAO;
import com.restaurant.management.model.Order;
import com.restaurant.management.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public void saveOrder(Order order) {
        String query = "INSERT INTO orders (table_id, user_id, order_time, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getTableId());
            stmt.setInt(2, order.getWaiterId());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getOrderTime()));
            stmt.setString(4, order.getStatus());

            stmt.executeUpdate();

            // Get generated order_id
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save order", e);
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOrder(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get order by ID", e);
        }

        return null;
    }

    @Override
    public List<Order> getOrdersByTable(int tableId) {
        String query = "SELECT * FROM orders WHERE table_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, tableId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get orders by table", e);
        }

        return orders;
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update order status", e);
        }
    }

    @Override
    public List<Order> getPendingOrders() {
        String query = "SELECT * FROM orders WHERE status = 'Pending'";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch pending orders", e);
        }

        return orders;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        return new Order(
                rs.getInt("order_id"),
                rs.getInt("table_id"),
                rs.getInt("user_id"),
                rs.getTimestamp("order_time").toLocalDateTime(),
                rs.getString("status")
        );
    }
}
