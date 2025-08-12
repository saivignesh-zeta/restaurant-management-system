package com.zeta.miniproject2.Restaurant.Management.System.Model.Entities;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.MenuCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "category", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private MenuCategory category;
}
