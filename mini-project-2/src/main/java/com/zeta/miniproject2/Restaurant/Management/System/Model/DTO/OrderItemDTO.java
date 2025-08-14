package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemDTO {
    private Integer menuItemId;
    private String menuItemName;
    private int quantity;
}
