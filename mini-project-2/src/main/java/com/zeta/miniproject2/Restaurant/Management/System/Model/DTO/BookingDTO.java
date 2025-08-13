package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingDTO {
    private Integer bookingId;
    private Integer customerId;
    private String customerName;
    private Integer tableId;
    private LocalDateTime bookingTime;
    private BookingStatus status;
}
