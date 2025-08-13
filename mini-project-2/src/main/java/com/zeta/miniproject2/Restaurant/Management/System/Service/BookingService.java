package com.zeta.miniproject2.Restaurant.Management.System.Service;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking getBookingById(Integer bookingId);

    List<Booking> getAllBookings();

    Booking updateBooking(Integer bookingId, Booking updatedBooking);

    Booking patchBooking(Integer bookingId, Booking updatedBooking);

    boolean deleteBooking(Integer bookingId);

    boolean existsById(Integer bookingId);
}

