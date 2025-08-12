package com.zeta.miniproject2.Restaurant.Management.System.Model.Entities;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Table(name = "users")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "user_name", nullable = false, length = 100)
        private String name;

        @Enumerated(EnumType.STRING)
        @Column(name = "role", nullable = false, length = 50)
        private UserRole role;
    }


