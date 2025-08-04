package com.restaurant.management.dao;

import com.restaurant.management.model.PreparedOrder;

import java.util.List;

public interface PreparedOrderDAO {

    void markItemPrepared(int orderId, int itemId);
    List<PreparedOrder> getPreparedItemsByOrderId(int orderId);

}
