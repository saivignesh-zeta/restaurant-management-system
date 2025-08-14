package com.zeta.miniproject2.Restaurant.Management.System.Repository;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {
}
