package com.zeta.miniproject2.Restaurant.Management.System.Model.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_name", nullable = false, length = 150)
    private String name;

    @Column(name = "contact", nullable = false, length = 50, unique = true)
    private String contact;
}
