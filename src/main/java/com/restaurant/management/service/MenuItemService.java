package com.restaurant.management.service;

import com.restaurant.management.model.MenuItem;
import java.util.List;

public interface MenuItemService {
    boolean addMenuItem(MenuItem item);
    boolean updateMenuItem(MenuItem item);
    boolean deleteMenuItem(int itemId);
    List<MenuItem> getAllItems();
    MenuItem getItemById(int itemId);
}
