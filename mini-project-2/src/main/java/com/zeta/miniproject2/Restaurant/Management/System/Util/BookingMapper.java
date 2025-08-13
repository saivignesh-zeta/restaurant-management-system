package com.zeta.miniproject2.Restaurant.Management.System.Util;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.BookingDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        return BookingDTO.builder()
                .bookingId(booking.getBookingId())
                .customerId(booking.getCustomer().getCustomerId())
                .customerName(booking.getCustomer().getName())
                .tableId(booking.getTable().getTableId())
                .bookingTime(booking.getBookingTime())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toEntity(BookingDTO dto, Customer customer, RestaurantTable table) {
        return Booking.builder()
                .bookingId(dto.getBookingId())
                .customer(customer)
                .table(table)
                .bookingTime(dto.getBookingTime())
                .status(dto.getStatus())
                .build();
    }

}
