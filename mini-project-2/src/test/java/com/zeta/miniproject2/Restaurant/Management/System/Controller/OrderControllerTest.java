package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.OrderDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.*;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.MenuCategory;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.TableStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.UserRole;
import com.zeta.miniproject2.Restaurant.Management.System.Service.MenuItemService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.OrderService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.RestaurantTableService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.UserService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock private OrderService orderService;
    @Mock private RestaurantTableService tableService;
    @Mock private UserService userService;
    @Mock private MenuItemService menuItemService;

    @InjectMocks private OrderController controller;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        RestaurantTable table = RestaurantTable.builder()
                .tableId(1)
                .capacity(4)
                .status(TableStatus.AVAILABLE)
                .build();

        User waiter = User.builder()
                .userId(1)
                .name("John Doe")
                .role(UserRole.WAITER)
                .build();

        order = Order.builder()
                .orderId(1)
                .status(OrderStatus.PLACED)
                .table(table)
                .waiter(waiter)
                .orderTime(LocalDateTime.now())
                .orderItems(new ArrayList<>())
                .build();
    }

    @Test
    void testGetAllOrders() {
        when(orderService.getAllOrders()).thenReturn(List.of(order));

        ResponseEntity<List<OrderDTO>> response = controller.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(order.getOrderId(), response.getBody().get(0).getOrderId());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById() {
        when(orderService.getOrderById(1)).thenReturn(order);

        ResponseEntity<OrderDTO> response = controller.getOrderById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order.getOrderId(), response.getBody().getOrderId());
        verify(orderService, times(1)).getOrderById(1);
    }

    @Test
    void testPlaceOrder() {
        when(orderService.placeOrder(order)).thenReturn(order);

        ResponseEntity<OrderDTO> response = controller.placeOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order.getOrderId(), response.getBody().getOrderId());
        verify(orderService, times(1)).placeOrder(order);
    }

    @Test
    void testPatchOrder() {
        // simulate patch with same order
        when(orderService.patchOrder(eq(1), any(Order.class))).thenReturn(order);

        ResponseEntity<OrderDTO> response = controller.patchOrder(1, OrderMapper.toDTO(order));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order.getOrderId(), response.getBody().getOrderId());
        verify(orderService, times(1)).patchOrder(eq(1), any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        when(orderService.deleteOrder(1)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteOrder(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).deleteOrder(1);
    }
}
