package com.zeta.miniproject2.Restaurant.Management.System.Repository;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Bill;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    // Find all bills for a given customer
    List<Bill> findByCustomerId(Integer customerId);

    // Find bills by status
    List<Bill> findByStatus(BillStatus status);

    // Find bills for a specific booking
    List<Bill> findByBookingId(Integer bookingId);

    // Find unpaid bills
    List<Bill> findByStatusOrderByCreatedAtDesc(BillStatus status);
}

