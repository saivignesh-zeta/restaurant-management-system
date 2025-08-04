package com.restaurant.management.service.impl;

import com.restaurant.management.dao.BillDAO;
import com.restaurant.management.dao.OrderItemDAO;
import com.restaurant.management.dao.PaymentDAO;
import com.restaurant.management.model.Bill;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.service.BillingService;

import java.time.LocalDateTime;
import java.util.List;

public class BillingServiceImpl implements BillingService {

    private final BillDAO billDAO;
    private final OrderItemDAO orderItemDAO;


    public BillingServiceImpl(BillDAO billDAO, OrderItemDAO orderItemDAO) {
        this.billDAO = billDAO;
        this.orderItemDAO = orderItemDAO;
    }

    //  New multi-order methods

    @Override
    public boolean isOrderBilled(int orderId) {
        return billDAO.isOrderBilled(orderId);
    }

    @Override
    public double calculateTotalAmount(List<Order> orders) {
        double total = 0;
        for (Order order : orders) {
            List<OrderItem> items = orderItemDAO.getItemsByOrderId(order.getOrderId());
            for (OrderItem item : items) {
                total += item.getMenuItem().getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    @Override
    public int generateBill(List<Order> orders, double totalAmount) {
        Bill bill = new Bill();
        bill.setTotalAmount(totalAmount);
        bill.setPaid(false);
        bill.setCreatedTime(LocalDateTime.now());
        bill.setOrders(orders); // assuming Bill has List<Order>

        return billDAO.insertBill(bill); // should return generated bill ID
    }

    @Override
    public boolean markBillAsPaid(int billId, String method) {
        return billDAO.updateBillAsPaid(billId, method, LocalDateTime.now());
    }

    @Override
    public List<Bill> getAllPaidBills() {
        return billDAO.getBillsByStatus(true); // true = paid
    }



}
