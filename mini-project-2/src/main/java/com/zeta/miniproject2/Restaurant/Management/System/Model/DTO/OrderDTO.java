package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderDTO {
    private int orderId;
    private int tableId;
    private int waiterId;
    private LocalDateTime orderTime;
    private OrderStatus status;
    private List<OrderItemDTO> items;
}


