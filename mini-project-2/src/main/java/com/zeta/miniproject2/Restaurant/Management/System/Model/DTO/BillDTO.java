package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BillStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BillDTO {
    private int billId;
    private double totalAmount;
    private BillStatus billStatus;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdTime;
    private LocalDateTime paidTime;
    private List<Integer> orderIds; // IDs only to keep it lightweight
}
