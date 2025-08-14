package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderDTO {
    private Integer orderId;
    private Integer tableId;
    private Integer waiterId;
    private LocalDateTime orderTime;
    private OrderStatus status;
    private List<OrderItemDTO> items;
}


