package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.OrderDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.OrderItemDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.*;
import com.zeta.miniproject2.Restaurant.Management.System.Service.MenuItemService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.OrderService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.RestaurantTableService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.UserService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.MenuItemMapper;
import com.zeta.miniproject2.Restaurant.Management.System.Util.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final RestaurantTableService restaurantTableService;
    private final UserService userService;
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody Order order) {
        log.info("API Request - Place new order: {}", order);
        OrderDTO saved = OrderMapper.toDTO(orderService.placeOrder(order));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        log.info("API Request - Get all orders");
        List<OrderDTO> orders = orderService.getAllOrders()
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        log.info("API Request - Get order by ID: {}", id);
        OrderDTO order = OrderMapper.toDTO(orderService.getOrderById(id));
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Integer id,
            @RequestBody Order updatedOrder
    ) {
        log.info("API Request - Update order ID: {}", id);
        Order savedOrder = orderService.updateOrder(id, updatedOrder);
        return ResponseEntity.ok(OrderMapper.toDTO(savedOrder));
    }

    @PatchMapping("/{id}/partial")
    public ResponseEntity<OrderDTO> patchOrder(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {

        RestaurantTable table = null;
        User waiter = null;
        List<MenuItem> menuItems = List.of(); // default to empty list
        List<OrderItem> orderItems = List.of(); // default to empty list

        if (orderDTO.getTableId() != 0) {
            table = restaurantTableService.getTableById(orderDTO.getTableId());
        }

        if (orderDTO.getWaiterId() != 0) {
            waiter = userService.getUserById(orderDTO.getWaiterId());
        }

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            menuItems = menuItemService.getMenuItemsByIds(
                    orderDTO.getItems().stream().map(OrderItemDTO::getMenuItemId).toList()
            );
        }

        Order order = new Order();
        orderItems = OrderMapper.toOrderItems(orderDTO.getItems(), order, menuItems);
        order.setOrderItems(orderItems);

        Order finalOrder = OrderMapper.toEntity(orderDTO, table, waiter, orderItems);

        log.info("API Request - Patch order ID: {}", id);
        Order savedOrder = orderService.patchOrder(id, finalOrder);
        return ResponseEntity.ok(OrderMapper.toDTO(savedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        log.info("API Request - Delete order by ID: {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
