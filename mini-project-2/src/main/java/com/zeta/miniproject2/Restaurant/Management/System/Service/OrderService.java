package com.zeta.miniproject2.Restaurant.Management.System.Service;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Order;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);

    List<Order> getAllOrders();

    Order updateOrder(Integer orderId, Order updatedOrder);

    Order patchOrder(Integer orderId, Order updatedOrder);

    Order getOrderById(Integer orderId);

    boolean deleteOrder(Integer orderId);

    boolean existsById(Integer orderId);

}
