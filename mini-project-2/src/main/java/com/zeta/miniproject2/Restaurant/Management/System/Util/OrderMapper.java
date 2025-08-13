package com.zeta.miniproject2.Restaurant.Management.System.Util;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.OrderDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.OrderItemDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .tableId(order.getTable().getTableId())
                .waiterId(order.getWaiter().getUserId())
                .orderTime(order.getOrderTime())
                .status(order.getStatus())
                .items(toItemDTOList(order.getOrderItems()))
                .build();
    }

    private static List<OrderItemDTO> toItemDTOList(List<OrderItem> orderItems) {
        if (orderItems == null) return List.of();
        return orderItems.stream()
                .map(oi -> OrderItemDTO.builder()
                        .menuItemId(oi.getMenuItem().getItemId())
                        .menuItemName(oi.getMenuItem().getName())
                        .quantity(oi.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public static Order toEntity(
            OrderDTO dto,
            RestaurantTable table,
            User waiter,
            List<OrderItem> orderItems
    ) {
        return Order.builder()
                .orderId(dto.getOrderId())
                .table(table)
                .waiter(waiter)
                .orderTime(dto.getOrderTime())
                .status(dto.getStatus())
                .orderItems(orderItems)
                .build();
    }

    public static List<OrderItem> toOrderItems(List<OrderItemDTO> dtos, Order order, List<MenuItem> menuItems) {
        return dtos.stream()
                .map(dto -> {
                    MenuItem menuItem = menuItems.stream()
                            .filter(mi -> mi.getItemId() == dto.getMenuItemId())
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("MenuItem not found with id: " + dto.getMenuItemId()));

                    return OrderItem.builder()
                            .order(order)
                            .menuItem(menuItem)
                            .quantity(dto.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
