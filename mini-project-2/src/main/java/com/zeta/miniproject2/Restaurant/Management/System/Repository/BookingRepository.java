package com.zeta.miniproject2.Restaurant.Management.System.Repository;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = """
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Booking b
    WHERE b.table.tableId = :tableId
      AND b.bookingTime = :bookingTime
      AND b.status = :status
""")
    boolean existsByTableIdAndBookingTimeAndStatus( int tableId,
                          LocalDateTime bookingTime,
                           BookingStatus status);

}

