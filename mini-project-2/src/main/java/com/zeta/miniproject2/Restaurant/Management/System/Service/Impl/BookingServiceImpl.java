package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BookingStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.BookingRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.BookingService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        log.info("Creating booking: {}", booking);

        boolean conflict = bookingRepository.existsByTableIdAndBookingTimeAndStatus(
                booking.getTable().getTableId(),
                booking.getBookingTime(),
                BookingStatus.CONFIRMED
        );

        if (conflict) {
            log.warn("Table {} is already booked at {}", booking.getTable().getTableId(), booking.getBookingTime());
            throw new IllegalStateException("Table already booked at that time");
        }

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created with ID: {}", savedBooking.getBookingId());
        return savedBooking;
    }

    @Override
    public Booking getBookingById(Integer bookingId) {
        log.info("Fetching booking ID: {}", bookingId);
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    log.warn("Booking with ID {} not found", bookingId);
                    return new ResourceNotFoundException("Booking not found with ID: " + bookingId);
                });
    }

    @Override
    public List<Booking> getAllBookings() {
        log.info("Fetching all bookings");
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            log.warn("No bookings found");
        } else {
            log.info("{} bookings found", bookings.size());
        }
        return bookings;
    }

    @Override
    public Booking updateBooking(Integer bookingId, Booking updatedBooking){
        log.info("Fully updating booking with ID: {}", bookingId);

        Booking existingBooking = getBookingById(bookingId);

        BeanUtils.copyProperties(updatedBooking, existingBooking, "bookingId");

        Booking savedBooking = bookingRepository.save(existingBooking);

        log.info("Booking with ID {} fully updated", bookingId);
        return savedBooking;
    }

    @Override
    public Booking patchBooking(Integer bookingId, Booking updatedBooking) {
        log.info("Updating booking ID: {}", bookingId);
        Booking existingBooking = getBookingById(bookingId);

        existingBooking = EntityUtil.copyNonNullProperties(updatedBooking, existingBooking);

        Booking saved = bookingRepository.save(existingBooking);
        log.info("Booking ID {} updated successfully", bookingId);
        return saved;
    }

    @Override
    public boolean deleteBooking(Integer bookingId) {
        log.info("Deleting booking ID: {}", bookingId);
        if (!bookingRepository.existsById(bookingId)) {
            log.warn("Booking ID {} does not exist", bookingId);
            throw new ResourceNotFoundException("Booking not found with ID: " + bookingId);
        }
        bookingRepository.deleteById(bookingId);
        log.info("Booking ID {} deleted successfully", bookingId);
        return true;
    }

    @Override
    public boolean existsById(Integer bookingId) {
        log.info("Checking existence of booking ID: {}", bookingId);
        return bookingRepository.existsById(bookingId);
    }
}
