package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.MenuItemDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.MenuCategory;
import com.zeta.miniproject2.Restaurant.Management.System.Service.MenuItemService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.MenuItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MenuItemControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuItemController controller;

    private MenuItem menuItem;
    private MenuItemDTO menuItemDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        menuItem = MenuItem.builder()
                .itemId(1)
                .name("Pasta")
                .price(12.5)
                .description("Delicious Italian pasta")
                .category(MenuCategory.valueOf("MAIN_COURSE"))
                .build();

        menuItemDTO = MenuItemMapper.toDTO(menuItem);
    }

    private void assertMenuItemDTOEquals(MenuItemDTO expected, MenuItemDTO actual) {
        assertEquals(expected.getItemId(), actual.getItemId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCategory(), actual.getCategory());
    }

    @Test
    void testCreateMenuItem() {
        when(menuItemService.createMenuItem(any(MenuItem.class))).thenReturn(menuItem);

        ResponseEntity<MenuItemDTO> response = controller.createMenuItem(menuItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertMenuItemDTOEquals(menuItemDTO, response.getBody());
        verify(menuItemService, times(1)).createMenuItem(menuItem);
    }

    @Test
    void testGetAllMenuItems() {
        when(menuItemService.getAllMenuItems()).thenReturn(Arrays.asList(menuItem));

        ResponseEntity<List<MenuItemDTO>> response = controller.getAllMenuItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertMenuItemDTOEquals(menuItemDTO, response.getBody().get(0));
        verify(menuItemService, times(1)).getAllMenuItems();
    }

    @Test
    void testGetMenuItemById() {
        when(menuItemService.getMenuItemById(1)).thenReturn(menuItem);

        ResponseEntity<MenuItemDTO> response = controller.getMenuItemById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertMenuItemDTOEquals(menuItemDTO, response.getBody());
        verify(menuItemService, times(1)).getMenuItemById(1);
    }

    @Test
    void testUpdateMenuItem() {
        when(menuItemService.updateMenuItem(eq(1), any(MenuItem.class))).thenReturn(menuItem);

        ResponseEntity<MenuItemDTO> response = controller.updateMenuItem(1, menuItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertMenuItemDTOEquals(menuItemDTO, response.getBody());
        verify(menuItemService, times(1)).updateMenuItem(1, menuItem);
    }

    @Test
    void testPatchMenuItem() {
        when(menuItemService.getMenuItemById(1)).thenReturn(menuItem);
        when(menuItemService.patchMenuItem(eq(1), any(MenuItem.class))).thenReturn(menuItem);

        ResponseEntity<MenuItemDTO> response = controller.patchMenuItem(1, menuItemDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertMenuItemDTOEquals(menuItemDTO, response.getBody());
        verify(menuItemService, times(1)).getMenuItemById(1);
        verify(menuItemService, times(1)).patchMenuItem(eq(1), any(MenuItem.class));
    }

    @Test
    void testDeleteMenuItem() {
        when(menuItemService.deleteMenuItem(1)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteMenuItem(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuItemService, times(1)).deleteMenuItem(1);
    }

}
