package com.zeta.miniproject2.Restaurant.Management.System.Repository;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Order;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findByBookingId(Integer bookingId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCreatedAtAfter(LocalDateTime dateTime);

    List<Order> findByStatusAndCreatedAtBetween(
            OrderStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}

