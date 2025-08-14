package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.MenuItemRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.MenuItemService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        log.info("Creating new menu item: {}", menuItem);
        MenuItem savedItem = menuItemRepository.save(menuItem);
        log.info("Menu item created with ID: {}", savedItem.getItemId());
        return savedItem;
    }

    @Override
    public MenuItem getMenuItemById(Integer menuItemId) {
        log.info("Fetching menu item with ID: {}", menuItemId);
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> {
                    log.warn("Menu item with ID {} not found", menuItemId);
                    return new ResourceNotFoundException("Menu item not found with ID: " + menuItemId);
                });
    }

    @Override
    public List<MenuItem> getMenuItemsByIds(List<Integer> itemIds) {
        return menuItemRepository.findAllById(itemIds);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        log.info("Fetching all menu items");
        List<MenuItem> menuItems = menuItemRepository.findAll();
        if (menuItems.isEmpty()) {
            log.warn("No menu items found");
        } else {
            log.info("Total menu items found: {}", menuItems.size());
        }
        return menuItems;
    }

    @Override
    public MenuItem updateMenuItem(Integer menuItemId, MenuItem updatedMenuItem){
        log.info("Fully updating menu item with ID: {}", menuItemId);
        MenuItem existingMenuItem = getMenuItemById(menuItemId);

        BeanUtils.copyProperties(updatedMenuItem, existingMenuItem, "itemId");
        MenuItem savedMenuItem = menuItemRepository.save(existingMenuItem);
        log.info("Menu item with ID {} successfully fully updated", menuItemId);

        return savedMenuItem;
    }

    @Override
    public MenuItem patchMenuItem(Integer menuItemId, MenuItem updatedMenuItem) {
        log.info("Updating menu item with ID: {}", menuItemId);
        MenuItem existingItem = getMenuItemById(menuItemId);

        existingItem = EntityUtil.copyNonNullProperties(updatedMenuItem, existingItem);

        MenuItem savedItem = menuItemRepository.save(existingItem);
        log.info("Menu item with ID {} successfully updated", menuItemId);
        return savedItem;
    }

    @Override
    public boolean deleteMenuItem(Integer menuItemId) {
        log.info("Attempting to delete menu item with ID: {}", menuItemId);
        if (!menuItemRepository.existsById(menuItemId)) {
            log.warn("Menu item with ID {} does not exist", menuItemId);
            throw new ResourceNotFoundException("Menu item not found with ID: " + menuItemId);
        }
        menuItemRepository.deleteById(menuItemId);
        log.info("Menu item with ID {} successfully deleted", menuItemId);
        return true;
    }

    @Override
    public boolean existsById(Integer id) {
        log.info("Checking existence of menu item with id {}", id);
        boolean exists = menuItemRepository.existsById(id);
        if (exists) {
            log.info("Menu item with id {} exists", id);
        } else {
            log.warn("Menu item with id {} does not exist", id);
        }
        return exists;
    }
}
