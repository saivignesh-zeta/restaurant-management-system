package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.PaymentDAO;
import com.restaurant.management.model.Payment;
import com.restaurant.management.util.DBConnection;

import java.sql.*;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public void savePayment(Payment payment) {
        String query = "INSERT INTO payments (order_id, amount, method, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getMethod());
            stmt.setString(4, payment.getStatus());

            stmt.executeUpdate();

            // Optionally retrieve generated payment ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                payment.setPaymentId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save payment", e);
        }
    }

    @Override
    public Payment getPaymentByOrderId(int orderId) {
        String query = "SELECT * FROM payments WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPayment(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch payment by order ID", e);
        }

        return null;
    }
    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getInt("payment_id"),
                rs.getInt("order_id"),
                rs.getDouble("amount"),
                rs.getString("method"),
                rs.getString("status")
        );
    }
}
