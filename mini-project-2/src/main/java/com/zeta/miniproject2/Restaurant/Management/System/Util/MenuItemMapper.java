package com.zeta.miniproject2.Restaurant.Management.System.Util;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.MenuItemDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;

public class MenuItemMapper {

    public static MenuItemDTO toDTO(MenuItem menuItem) {
        return MenuItemDTO.builder()
                .itemId(menuItem.getItemId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .category(menuItem.getCategory())
                .build();
    }

    public static MenuItem toEntity(MenuItemDTO menuItemDTO) {
        return MenuItem.builder()
                .itemId(menuItemDTO.getItemId())
                .name(menuItemDTO.getName())
                .description(menuItemDTO.getDescription())
                .price(menuItemDTO.getPrice())
                .category(menuItemDTO.getCategory())
                .build();
    }
}
