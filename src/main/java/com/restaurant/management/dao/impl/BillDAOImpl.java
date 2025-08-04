package com.restaurant.management.dao.impl;

import com.restaurant.management.dao.BillDAO;
import com.restaurant.management.model.Bill;
import com.restaurant.management.model.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {

    private final Connection connection;

    public BillDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insertBill(Bill bill) {
        String sql = "INSERT INTO bills (total_amount, is_paid, payment_method, created_time, paid_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, bill.getTotalAmount());
            stmt.setBoolean(2, bill.isPaid());
            stmt.setString(3, bill.getPaymentMethod());
            stmt.setTimestamp(4, Timestamp.valueOf(bill.getCreatedTime()));
            stmt.setTimestamp(5, bill.getPaidTime() != null ? Timestamp.valueOf(bill.getPaidTime()) : null);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // return generated bill_id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void linkOrdersToBill(int billId, List<Integer> orderIds) {
        String sql = "INSERT INTO bill_orders (bill_id, order_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int orderId : orderIds) {
                stmt.setInt(1, billId);
                stmt.setInt(2, orderId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isOrderBilled(int orderId) {
        String sql = "SELECT 1 FROM bill_orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // if row exists, order is billed
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBillAsPaid(int billId, String method, LocalDateTime paidTime) {
        String sql = "UPDATE bills SET is_paid = ?, payment_method = ?, paid_time = ? WHERE bill_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            stmt.setString(2, method);
            stmt.setTimestamp(3, Timestamp.valueOf(paidTime));
            stmt.setInt(4, billId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Bill> getBillsByStatus(boolean isPaid) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE is_paid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isPaid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaid(rs.getBoolean("is_paid"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
                Timestamp paidTs = rs.getTimestamp("paid_time");
                bill.setPaidTime(paidTs != null ? paidTs.toLocalDateTime() : null);
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public Bill getBillById(int billId) {
        String sql = "SELECT * FROM bills WHERE bill_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaid(rs.getBoolean("is_paid"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
                Timestamp paidTs = rs.getTimestamp("paid_time");
                bill.setPaidTime(paidTs != null ? paidTs.toLocalDateTime() : null);
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
