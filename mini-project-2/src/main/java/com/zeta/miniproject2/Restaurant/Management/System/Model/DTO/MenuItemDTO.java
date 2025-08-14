package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.MenuCategory;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItemDTO {
    private Integer itemId;
    private String name;
    private String description;
    private Double price;
    private MenuCategory category;
}
