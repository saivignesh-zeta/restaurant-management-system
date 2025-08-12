package com.zeta.miniproject2.Restaurant.Management.System.Repository;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByCustomerId(int customerId);

    List<Booking> findByTableIdAndStatus(int tableId, BookingStatus status);

    List<Booking> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByTableIdAndBookingTimeAndStatus(int tableId, LocalDateTime bookingTime, BookingStatus status);
}

