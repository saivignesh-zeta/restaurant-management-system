package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.MenuCategory;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.MenuItemRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menuItem = MenuItem.builder()
                .itemId(1)
                .name("Pasta")
                .description("Delicious creamy pasta")
                .price(250.0)
                .category(MenuCategory.MAIN_COURSE)
                .build();
    }

    @Test
    void testCreateMenuItem() {
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem created = menuItemService.createMenuItem(menuItem);

        assertNotNull(created);
        assertEquals("Pasta", created.getName());
        verify(menuItemRepository, times(1)).save(menuItem);
    }

    @Test
    void testGetMenuItemById_Found() {
        when(menuItemRepository.findById(1)).thenReturn(Optional.of(menuItem));

        MenuItem found = menuItemService.getMenuItemById(1);

        assertNotNull(found);
        assertEquals(1, found.getItemId());
        verify(menuItemRepository, times(1)).findById(1);
    }

    @Test
    void testGetMenuItemById_NotFound() {
        when(menuItemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> menuItemService.getMenuItemById(999));
    }

    @Test
    void testGetMenuItemsByIds() {
        when(menuItemRepository.findAllById(anyList())).thenReturn(Arrays.asList(menuItem));

        List<MenuItem> items = menuItemService.getMenuItemsByIds(Arrays.asList(1, 2));

        assertEquals(1, items.size());
        verify(menuItemRepository, times(1)).findAllById(anyList());
    }

    @Test
    void testGetAllMenuItems() {
        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(menuItem));

        List<MenuItem> items = menuItemService.getAllMenuItems();

        assertEquals(1, items.size());
        verify(menuItemRepository, times(1)).findAll();
    }

    @Test
    void testUpdateMenuItem() {
        MenuItem updated = MenuItem.builder()
                .itemId(1)
                .name("Updated Pasta")
                .description("Updated desc")
                .price(300.0)
                .category(MenuCategory.MAIN_COURSE)
                .build();

        when(menuItemRepository.findById(1)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(updated);

        MenuItem result = menuItemService.updateMenuItem(1, updated);

        assertEquals("Updated Pasta", result.getName());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }

    @Test
    void testPatchMenuItem() {
        MenuItem patch = new MenuItem();
        patch.setName("Half Updated Pasta");

        when(menuItemRepository.findById(1)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem result = menuItemService.patchMenuItem(1, patch);

        assertEquals("Half Updated Pasta", result.getName());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }

    @Test
    void testDeleteMenuItem_Found() {
        when(menuItemRepository.existsById(1)).thenReturn(true);
        doNothing().when(menuItemRepository).deleteById(1);

        boolean result = menuItemService.deleteMenuItem(1);

        assertTrue(result);
        verify(menuItemRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteMenuItem_NotFound() {
        when(menuItemRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> menuItemService.deleteMenuItem(1));
    }

    @Test
    void testExistsById() {
        when(menuItemRepository.existsById(1)).thenReturn(true);

        boolean exists = menuItemService.existsById(1);

        assertTrue(exists);
        verify(menuItemRepository, times(1)).existsById(1);
    }
}
