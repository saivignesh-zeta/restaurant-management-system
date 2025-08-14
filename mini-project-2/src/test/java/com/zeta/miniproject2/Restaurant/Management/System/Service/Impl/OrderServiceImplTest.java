package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.*;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private OrderItemRepository orderItemRepository;
    @Mock private MenuItemRepository menuItemRepository;
    @Mock private UserRepository userRepository;
    @Mock private RestaurantTableRepository tableRepository;

    @InjectMocks
    private OrderServiceImpl service;

    private User waiter;
    private RestaurantTable table;
    private MenuItem menu1;

    @BeforeEach
    void setUp() {
        waiter = new User();
        waiter.setUserId(10);

        table = new RestaurantTable();
        table.setTableId(5);

        menu1 = new MenuItem();
        menu1.setItemId(100);
    }

    @Test
    void placeOrder_success() {
        Order input = new Order();
        input.setWaiter(waiter);
        input.setTable(table);
        input.setStatus(OrderStatus.PLACED);
        input.setOrderTime(LocalDateTime.now());

        OrderItem oi = new OrderItem();
        oi.setMenuItem(menu1);
        oi.setQuantity(2);
        input.setOrderItems(new ArrayList<>(List.of(oi)));

        when(userRepository.findById(10)).thenReturn(Optional.of(waiter));
        when(tableRepository.findById(5)).thenReturn(Optional.of(table));
        when(menuItemRepository.findById(100)).thenReturn(Optional.of(menu1));

        Order saved = new Order();
        saved.setOrderId(1);
        saved.setWaiter(waiter);
        saved.setTable(table);
        saved.setStatus(OrderStatus.PLACED);
        saved.setOrderItems(List.of(oi));

        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        Order result = service.placeOrder(input);

        assertNotNull(result);
        assertEquals(1, result.getOrderId());
        assertEquals(OrderStatus.PLACED, result.getStatus());
        assertEquals(1, result.getOrderItems().size());
        assertEquals(100, result.getOrderItems().get(0).getMenuItem().getItemId());

        verify(userRepository).findById(10);
        verify(tableRepository).findById(5);
        verify(menuItemRepository).findById(100);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void placeOrder_missingWaiter_throws() {
        Order input = new Order();
        input.setWaiter(waiter);
        input.setTable(table);
        input.setOrderItems(Collections.emptyList());

        when(userRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.placeOrder(input));
        verifyNoInteractions(tableRepository, menuItemRepository, orderRepository);
    }

    @Test
    void getOrderById_found() {
        Order o = new Order();
        o.setOrderId(7);
        when(orderRepository.findById(7)).thenReturn(Optional.of(o));

        Order result = service.getOrderById(7);

        assertEquals(7, result.getOrderId());
        verify(orderRepository).findById(7);
    }

    @Test
    void getOrderById_notFound_throws() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getOrderById(99));
    }

    @Test
    void updateOrder_fullCopy() {
        Order existing = new Order();
        existing.setOrderId(9);
        existing.setStatus(OrderStatus.PLACED);
        existing.setOrderItems(new ArrayList<>());

        Order updated = new Order();
        updated.setStatus(OrderStatus.SERVED);
        updated.setTable(table);
        updated.setWaiter(waiter);
        updated.setOrderItems(new ArrayList<>());

        when(orderRepository.findById(9)).thenReturn(Optional.of(existing));
        when(tableRepository.findById(5)).thenReturn(Optional.of(table));
        when(userRepository.findById(10)).thenReturn(Optional.of(waiter));
        when(orderRepository.save(existing)).thenReturn(existing);

        Order result = service.updateOrder(9, updated);

        assertEquals(OrderStatus.SERVED, result.getStatus());
        verify(orderRepository).save(existing);
    }

    @Test
    void patchOrder_updateStatusAndItems() {
        Order existing = new Order();
        existing.setOrderId(11);
        existing.setStatus(OrderStatus.PLACED);
        existing.setOrderItems(new ArrayList<>());

        OrderItem updatedItem = new OrderItem();
        updatedItem.setMenuItem(menu1);
        updatedItem.setQuantity(3);

        Order updated = new Order();
        updated.setStatus(OrderStatus.IN_PROGRESS);
        updated.setOrderItems(List.of(updatedItem));

        when(orderRepository.findById(11)).thenReturn(Optional.of(existing));
        when(menuItemRepository.findById(100)).thenReturn(Optional.of(menu1));
        when(orderRepository.save(existing)).thenReturn(existing);

        Order result = service.patchOrder(11, updated);

        assertEquals(OrderStatus.IN_PROGRESS, result.getStatus());
        assertEquals(1, result.getOrderItems().size());
        assertEquals(100, result.getOrderItems().get(0).getMenuItem().getItemId());

        verify(orderRepository).findById(11);
        verify(orderRepository).save(existing);
    }

    @Test
    void deleteOrder_exists_true() {
        when(orderRepository.existsById(4)).thenReturn(true);
        assertTrue(service.deleteOrder(4));
        verify(orderRepository).deleteById(4);
    }

    @Test
    void deleteOrder_notExists_throws() {
        when(orderRepository.existsById(400)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteOrder(400));
        verify(orderRepository, never()).deleteById(anyInt());
    }
}
