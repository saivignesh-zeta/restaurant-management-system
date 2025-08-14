package com.zeta.miniproject2.Restaurant.Management.System.Util;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.BillDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Bill;

import java.util.stream.Collectors;

public class BillMapper {

    public static BillDTO toDTO(Bill bill) {
        return BillDTO.builder()
                .billId(bill.getBillId())
                .totalAmount(bill.getTotalAmount())
                .billStatus(bill.getBillStatus())
                .paymentMethod(bill.getPaymentMethod())
                .createdTime(bill.getCreatedTime())
                .paidTime(bill.getPaidTime())
                .orderIds(
                        bill.getOrders() != null
                                ? bill.getOrders().stream().map(o -> o.getOrderId()).collect(Collectors.toList())
                                : null
                )
                .build();
    }

    public static Bill toEntity(BillDTO dto) {
        return Bill.builder()
                .billId(dto.getBillId())
                .totalAmount(dto.getTotalAmount())
                .billStatus(dto.getBillStatus())
                .paymentMethod(dto.getPaymentMethod())
                .createdTime(dto.getCreatedTime())
                .paidTime(dto.getPaidTime())
                .orders(null)
                .build();
    }

}
