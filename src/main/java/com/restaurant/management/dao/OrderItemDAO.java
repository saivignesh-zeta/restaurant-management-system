package com.restaurant.management.dao;

import com.restaurant.management.model.OrderItem;

import java.util.List;

public interface OrderItemDAO {

    void saveOrderItem(OrderItem item);
    List<OrderItem> getItemsByOrderId(int orderId);


}
