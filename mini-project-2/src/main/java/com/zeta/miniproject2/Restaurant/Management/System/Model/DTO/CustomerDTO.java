package com.zeta.miniproject2.Restaurant.Management.System.Model.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerDTO {
    private Integer customerId;
    private String name;
    private String contact;
}
