package com.zeta.miniproject2.Restaurant.Management.System.Model.Entities;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "table_id", nullable = false)
    private int tableId;

    @Column(name = "waiter_id", nullable = false)
    private int waiterId;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

}
