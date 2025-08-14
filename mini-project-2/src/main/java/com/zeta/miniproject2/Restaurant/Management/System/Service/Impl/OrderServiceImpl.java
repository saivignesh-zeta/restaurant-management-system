package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Order;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.OrderItem;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.User;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.MenuItemRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.OrderItemRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.OrderRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.RestaurantTableRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements com.zeta.miniproject2.Restaurant.Management.System.Service.OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final RestaurantTableRepository tableRepository;

    @Transactional
    @Override
    public Order placeOrder(Order inputOrder) {

        if (inputOrder.getWaiter() == null || inputOrder.getWaiter().getUserId() == null) {
            throw new IllegalArgumentException("Waiter is required");
        }
        User waiter = userRepository.findById(inputOrder.getWaiter().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Waiter not found: " + inputOrder.getWaiter().getUserId()));
        inputOrder.setWaiter(waiter);


        if (inputOrder.getTable() == null || inputOrder.getTable().getTableId() == null) {
            throw new IllegalArgumentException("Table is required");
        }
        RestaurantTable table = tableRepository.findById(inputOrder.getTable().getTableId())
                .orElseThrow(() -> new EntityNotFoundException("Table not found: " + inputOrder.getTable().getTableId()));
        inputOrder.setTable(table);


        if (inputOrder.getOrderTime() == null) {
            inputOrder.setOrderTime(LocalDateTime.now());
        }


        List<OrderItem> processedItems = new ArrayList<>();
        if (inputOrder.getOrderItems() != null) {
            for (OrderItem item : inputOrder.getOrderItems()) {
                if (item.getMenuItem() == null || item.getMenuItem().getItemId() == null) {
                    throw new IllegalArgumentException("OrderItem.menuItemId is required");
                }
                MenuItem menuItem = menuItemRepository.findById(item.getMenuItem().getItemId())
                        .orElseThrow(() -> new EntityNotFoundException("MenuItem not found with ID: " + item.getMenuItem().getItemId()));
                item.setMenuItem(menuItem);
                item.setOrder(inputOrder);
                processedItems.add(item);
            }
        }
        inputOrder.setOrderItems(processedItems);

        return orderRepository.save(inputOrder);
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            log.warn("No orders found");
        } else {
            log.info("Total orders found: {}", orders.size());
        }
        return orders;
    }

    @Override
    public Order updateOrder(Integer orderId, Order updatedOrder) {
        log.info("Fully updating order with ID: {}", orderId);

        Order existingOrder = getOrderById(orderId);


        BeanUtils.copyProperties(updatedOrder, existingOrder, "orderId", "orderItems", "table", "waiter");


        if (updatedOrder.getTable() != null && updatedOrder.getTable().getTableId() != null) {
            RestaurantTable managedTable = tableRepository.findById(updatedOrder.getTable().getTableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Table not found: " + updatedOrder.getTable().getTableId()));
            existingOrder.setTable(managedTable);
        }
        if (updatedOrder.getWaiter() != null && updatedOrder.getWaiter().getUserId() != null) {
            User managedWaiter = userRepository.findById(updatedOrder.getWaiter().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Waiter not found: " + updatedOrder.getWaiter().getUserId()));
            existingOrder.setWaiter(managedWaiter);
        }
        if (updatedOrder.getOrderItems() != null) {

            existingOrder.getOrderItems().clear();
            for (OrderItem item : updatedOrder.getOrderItems()) {
                if (item.getMenuItem() == null || item.getMenuItem().getItemId() == null) {
                    throw new IllegalArgumentException("MenuItem ID is required for each OrderItem");
                }
                MenuItem managedMenu = menuItemRepository.findById(item.getMenuItem().getItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found: " + item.getMenuItem().getItemId()));
                item.setMenuItem(managedMenu);
                item.setOrder(existingOrder);
                existingOrder.getOrderItems().add(item);
            }
        }

        Order savedOrder = orderRepository.save(existingOrder);
        log.info("Order with ID {} successfully fully updated", orderId);
        return savedOrder;
    }

    @Override
    @Transactional
    public Order patchOrder(Integer id, Order updatedOrder) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (updatedOrder.getTable() != null && updatedOrder.getTable().getTableId() != null) {
            RestaurantTable managedTable = tableRepository.findById(updatedOrder.getTable().getTableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Table not found: " + updatedOrder.getTable().getTableId()));
            existing.setTable(managedTable);
        }
        if (updatedOrder.getWaiter() != null && updatedOrder.getWaiter().getUserId() != null) {
            User managedWaiter = userRepository.findById(updatedOrder.getWaiter().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Waiter not found: " + updatedOrder.getWaiter().getUserId()));
            existing.setWaiter(managedWaiter);
        }
        if (updatedOrder.getStatus() != null) {
            existing.setStatus(updatedOrder.getStatus());
        }
        if (updatedOrder.getOrderTime() != null) {
            existing.setOrderTime(updatedOrder.getOrderTime());
        }

        if (updatedOrder.getOrderItems() != null) {

            existing.getOrderItems().clear();
            for (OrderItem item : updatedOrder.getOrderItems()) {
                if (item.getMenuItem() == null || item.getMenuItem().getItemId() == null) {
                    throw new IllegalArgumentException("MenuItem ID is required for each OrderItem");
                }
                MenuItem managedMenu = menuItemRepository.findById(item.getMenuItem().getItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found: " + item.getMenuItem().getItemId()));
                item.setMenuItem(managedMenu);
                item.setOrder(existing);
                existing.getOrderItems().add(item);
            }
        }

        return orderRepository.save(existing);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        log.info("Fetching order by ID: {}", orderId);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.warn("Order with ID {} not found", orderId);
                    return new ResourceNotFoundException("Order not found with ID: " + orderId);
                });
    }

    @Override
    public boolean deleteOrder(Integer orderId) {
        log.info("Deleting order with ID: {}", orderId);
        if (!orderRepository.existsById(orderId)) {
            log.warn("Order ID {} does not exist", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
        log.info("Order ID {} deleted successfully", orderId);
        return true;
    }

    @Override
    public boolean existsById(Integer orderId) {
        log.info("Checking existence of order ID: {}", orderId);
        boolean exists = orderRepository.existsById(orderId);
        if (exists) {
            log.info("Order with ID {} exists", orderId);
        } else {
            log.warn("Order with ID {} does not exist", orderId);
        }
        return exists;
    }
}
