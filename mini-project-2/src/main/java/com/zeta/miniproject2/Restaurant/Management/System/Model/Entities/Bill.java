package com.zeta.miniproject2.Restaurant.Management.System.Model.Entities;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BillStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private int billId;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "bill_status", nullable = false)
    private BillStatus billStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "paid_time")
    private LocalDateTime paidTime;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
