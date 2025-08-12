package com.restaurant.management.dao;

import com.restaurant.management.model.RestaurantTable;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantTableDAO {

    List<RestaurantTable> getAllTables();
    RestaurantTable getTableById(int tableId);
    void updateTableStatus(int tableId, String status);
    List<RestaurantTable> getAvailableTablesByTime(LocalDateTime time);

}
