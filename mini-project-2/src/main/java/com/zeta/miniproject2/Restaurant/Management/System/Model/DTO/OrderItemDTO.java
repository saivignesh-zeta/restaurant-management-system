package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemDTO {
    private int menuItemId;
    private String menuItemName;
    private int quantity;
}
