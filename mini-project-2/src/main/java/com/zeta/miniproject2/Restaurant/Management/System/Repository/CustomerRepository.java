package com.zeta.miniproject2.Restaurant.Management.System.Repository;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
