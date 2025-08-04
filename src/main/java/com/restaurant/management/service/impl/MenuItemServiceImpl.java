package com.restaurant.management.service.impl;

import com.restaurant.management.dao.MenuItemDAO;
import com.restaurant.management.dao.impl.MenuItemDAOImpl;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.service.MenuItemService;

import java.util.List;

public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

    @Override
    public boolean addMenuItem(MenuItem item) {
        return menuItemDAO.addMenuItem(item);
    }

    @Override
    public boolean updateMenuItem(MenuItem item) {
        return menuItemDAO.updateMenuItem(item);
    }

    @Override
    public boolean deleteMenuItem(int itemId) {
        return menuItemDAO.deleteMenuItem(itemId);
    }

    @Override
    public List<MenuItem> getAllItems() {
        return menuItemDAO.getAllItems();
    }

    @Override
    public MenuItem getItemById(int itemId) {
        return menuItemDAO.getItemById(itemId);
    }
}
