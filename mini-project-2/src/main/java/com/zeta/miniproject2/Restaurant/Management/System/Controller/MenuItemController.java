package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.MenuItemDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;
import com.zeta.miniproject2.Restaurant.Management.System.Service.MenuItemService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import com.zeta.miniproject2.Restaurant.Management.System.Util.MenuItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('MANAGER')")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody MenuItem menuItem) {
        log.info("API Request - Create menu item: {}", menuItem);
        return new ResponseEntity<>(
                MenuItemMapper.toDTO(menuItemService.createMenuItem(menuItem)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        log.info("API Request - Get all menu items");
        return ResponseEntity.ok(
                menuItemService.getAllMenuItems()
                        .stream()
                        .map(MenuItemMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Integer id) {
        log.info("API Request - Get menu item ID: {}", id);
        return ResponseEntity.ok(
                MenuItemMapper.toDTO(menuItemService.getMenuItemById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable Integer id, @RequestBody MenuItem menuItem) {
        log.info("API Request - Update menu item ID: {}", id);
        return ResponseEntity.ok(
                MenuItemMapper.toDTO(menuItemService.updateMenuItem(id, menuItem))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuItemDTO> patchMenuItem(@PathVariable Integer id, @RequestBody MenuItemDTO menuItemDTO) {
        log.info("API Request - Patch menu item ID: {}", id);
        MenuItem existing = menuItemService.getMenuItemById(id);
        MenuItem menuItem = MenuItemMapper.toEntity(menuItemDTO);
        existing = EntityUtil.copyNonNullProperties(menuItem, existing);
        return ResponseEntity.ok(
                MenuItemMapper.toDTO(menuItemService.patchMenuItem(id, existing))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Integer id) {
        log.info("API Request - Delete menu item ID: {}", id);
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
