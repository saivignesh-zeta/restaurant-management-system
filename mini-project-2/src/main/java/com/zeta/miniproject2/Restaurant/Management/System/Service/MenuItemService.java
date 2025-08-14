package com.zeta.miniproject2.Restaurant.Management.System.Service;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;

import java.util.List;

public interface MenuItemService {

    MenuItem createMenuItem(MenuItem menuItem);

    MenuItem getMenuItemById(Integer id);

    public List<MenuItem> getMenuItemsByIds(List<Integer> itemIds);

    List<MenuItem> getAllMenuItems();

    MenuItem updateMenuItem(Integer id, MenuItem menuItem);

    MenuItem patchMenuItem(Integer id, MenuItem menuItem);

    boolean deleteMenuItem(Integer id);

    boolean existsById(Integer id);
}

