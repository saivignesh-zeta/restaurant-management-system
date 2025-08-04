package com.restaurant.management.dao;

import com.restaurant.management.model.MenuItem;

import java.util.List;

public interface MenuItemDAO {

    boolean addMenuItem(MenuItem item);
    boolean updateMenuItem(MenuItem item);
    boolean deleteMenuItem(int itemId);
    MenuItem getItemById(int itemId);
    List<MenuItem> getAllItems();

}
