package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.MenuItemDAO;
import com.restaurant.management.dao.OrderItemDAO;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public void saveOrderItem(OrderItem item) {
        String query = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getItemId());
            stmt.setInt(3, item.getQuantity());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save order item", e);
        }
    }

    @Override
    public List<OrderItem> getItemsByOrderId(int orderId) {
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();

        // DAO to fetch MenuItem by item_id
        MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = mapResultSetToOrderItem(rs);

                // Fetch the MenuItem and attach
                MenuItem menuItem = menuItemDAO.getItemById(item.getItemId());
                item.setMenuItem(menuItem);

                items.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch order items", e);
        }

        return items;
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();
        item.setOrderId(rs.getInt("order_id"));
        item.setItemId(rs.getInt("item_id"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }
}
