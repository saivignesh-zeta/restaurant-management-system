package com.restaurant.management.dao;

import com.restaurant.management.model.Payment;

public interface PaymentDAO {

    void savePayment(Payment payment);
    Payment getPaymentByOrderId(int orderId);

}
