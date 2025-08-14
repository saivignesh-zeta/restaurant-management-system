package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.TableStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantTableDTO {
    private Integer tableId;
    private int capacity;
    private TableStatus status;
}
