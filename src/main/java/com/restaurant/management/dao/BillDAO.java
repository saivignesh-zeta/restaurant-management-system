package com.restaurant.management.dao;

import com.restaurant.management.model.Bill;
import java.time.LocalDateTime;
import java.util.List;

public interface BillDAO {

    // Insert a new bill and return generated bill_id
    int insertBill(Bill bill);

    // Link orders to the bill in a mapping table (bill_orders)
    void linkOrdersToBill(int billId, List<Integer> orderIds);

    // Check if a given order has already been billed
    boolean isOrderBilled(int orderId);

    // Update a bill as paid with payment method and time
    boolean updateBillAsPaid(int billId, String paymentMethod, LocalDateTime paidTime);

    // Fetch all paid bills
    List<Bill> getBillsByStatus(boolean isPaid);

    // Optional: Get bill details including associated orders
    Bill getBillById(int billId);
}
