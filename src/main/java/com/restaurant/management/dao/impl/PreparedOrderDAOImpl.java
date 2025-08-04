package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.PreparedOrderDAO;
import com.restaurant.management.model.PreparedOrder;
import com.restaurant.management.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreparedOrderDAOImpl implements PreparedOrderDAO {
    @Override
    public void markItemPrepared(int orderId, int itemId) {
        String query = "UPDATE prepared_orders SET status = 'Prepared' WHERE order_id = ? AND item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, itemId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to mark item as prepared", e);
        }
    }

    @Override
    public List<PreparedOrder> getPreparedItemsByOrderId(int orderId) {
        String query = "SELECT * FROM prepared_orders WHERE order_id = ?";
        List<PreparedOrder> preparedItems = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                preparedItems.add(mapResultSetToPreparedOrder(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch prepared items", e);
        }

        return preparedItems;
    }
    private PreparedOrder mapResultSetToPreparedOrder(ResultSet rs) throws SQLException {
        return new PreparedOrder(
                rs.getInt("prepared_id"),
                rs.getInt("order_id"),
                rs.getInt("item_id"),
                rs.getString("status")
        );
    }
}
