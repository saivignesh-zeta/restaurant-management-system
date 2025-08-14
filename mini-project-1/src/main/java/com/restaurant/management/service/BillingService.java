package com.restaurant.management.service;

import com.restaurant.management.model.Bill;
import com.restaurant.management.model.Order;


import java.util.List;

public interface BillingService {
    boolean isOrderBilled(int orderId);
    double calculateTotalAmount(List<Order> orders);
    int generateBill(List<Order> orders, double totalAmount);
    boolean markBillAsPaid(int billId, String method);
    List<Bill> getAllPaidBills();
}
