package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.MenuItem;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Order;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.OrderItem;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.MenuItemRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.OrderItemRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.OrderRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.OrderService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public Order placeOrder(Order order) {
        log.info("Placing new order: {}", order);

        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PLACED);
        }

        Order savedOrder = orderRepository.save(order);

        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem orderItem : order.getOrderItems()) {

                if (orderItem.getMenuItem() != null && orderItem.getMenuItem().getItemId() != null) {
                    MenuItem menuItem = menuItemRepository.findById(orderItem.getMenuItem().getItemId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Menu item not found with ID: " + orderItem.getMenuItem().getItemId()));
                    orderItem.setMenuItem(menuItem);
                }

                orderItem.setOrder(savedOrder);
            }
            orderItemRepository.saveAll(order.getOrderItems());
        }

        log.info("Order placed successfully with ID: {}", savedOrder.getOrderId());
        return savedOrder;
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
    public Order updateOrder(Integer orderId, Order updatedOrder){
        log.info("Fully updating order with ID: {}", orderId);

        Order existingOrder = getOrderById(orderId);

        BeanUtils.copyProperties(updatedOrder, existingOrder, "orderId");

        Order savedOrder = orderRepository.save(existingOrder);

        log.info("Order with ID {} successfully fully updated", orderId);

        return savedOrder;
    }

    @Override
    public Order patchOrder(Integer orderId, Order updatedOrder) {
        log.info("Updating order with ID: {}", orderId);

        Order existingOrder = getOrderById(orderId);

        existingOrder = EntityUtil.copyNonNullProperties(updatedOrder, existingOrder);

        Order savedOrder = orderRepository.save(existingOrder);

        log.info("Order with ID {} successfully updated", orderId);

        return savedOrder;
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
