package com.zeta.miniproject2.Restaurant.Management.System.Repository;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
