package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.MenuItemDAO;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAOImpl implements MenuItemDAO {
    @Override
    public boolean addMenuItem(MenuItem item) {
        String query = "INSERT INTO menu_items (name, description, price, category) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setString(4, item.getCategory());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add menu item", e);
        }

    }

    @Override
    public boolean updateMenuItem(MenuItem item) {
        String query = "UPDATE menu_items SET name = ?, description = ?, price = ?, category = ? WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setString(4, item.getCategory());
            stmt.setInt(5, item.getItemId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update menu item", e);
        }

    }

    @Override
    public boolean deleteMenuItem(int itemId) {
        String query = "DELETE FROM menu_items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete menu item", e);
        }
    }

    @Override
    public MenuItem getItemById(int itemId) {
        String query = "SELECT * FROM menu_items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToMenuItem(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch menu item by ID", e);
        }

        return null;
    }

    @Override
    public List<MenuItem> getAllItems() {
        String sql = "SELECT * FROM menu_items";
        List<MenuItem> items = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                items.add(mapResultSetToMenuItem(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all menu items", e);
        }

        return items;
    }

    private MenuItem mapResultSetToMenuItem(ResultSet rs) throws SQLException {
        return new MenuItem(
                rs.getInt("item_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getString("category")
        );
    }
}
