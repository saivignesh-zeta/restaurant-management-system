package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BookingStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.BookingRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.CustomerRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.RestaurantTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock private BookingRepository bookingRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private RestaurantTableRepository tableRepository;

    @InjectMocks private BookingServiceImpl service;

    private Customer customer;
    private RestaurantTable table;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Alice");
        customer.setContact("1234567890");

        table = new RestaurantTable();
        table.setTableId(10);
    }
    @Test
    void testCreateBooking_success() {

        Booking input = Booking.builder()
                .customer(customer)
                .table(table)
                .bookingTime(LocalDateTime.now())
                .status(BookingStatus.PENDING)
                .build();


        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setBookingId(100); // simulate generated ID
            return b;
        });

        Booking saved = service.createBooking(input);

        assertNotNull(saved);
        assertEquals(100, saved.getBookingId());
        assertEquals(BookingStatus.PENDING, saved.getStatus());

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetBookingById_found() {
        Booking b = new Booking();
        b.setBookingId(5);
        when(bookingRepository.findById(5)).thenReturn(Optional.of(b));

        Booking result = service.getBookingById(5);

        assertEquals(5, result.getBookingId());
        verify(bookingRepository).findById(5);
    }

    @Test
    void testGetBookingById_notFound_throws() {
        when(bookingRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getBookingById(999));
    }

    @Test
    void testGetAllBookings() {
        Booking b1 = new Booking();
        b1.setBookingId(1);
        Booking b2 = new Booking();
        b2.setBookingId(2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Booking> bookings = service.getAllBookings();
        assertEquals(2, bookings.size());
        verify(bookingRepository).findAll();
    }

    @Test
    void testUpdateBooking_success() {
        Booking existing = new Booking();
        existing.setBookingId(7);
        existing.setStatus(BookingStatus.PENDING);

        Booking updated = new Booking();
        updated.setStatus(BookingStatus.CONFIRMED);
        updated.setCustomer(customer);
        updated.setTable(table);

        when(bookingRepository.findById(7)).thenReturn(Optional.of(existing));
        when(bookingRepository.save(existing)).thenReturn(existing);

        Booking result = service.updateBooking(7, updated);

        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
        verify(bookingRepository).save(existing);
    }

    @Test
    void testDeleteBooking_success() {
        when(bookingRepository.existsById(3)).thenReturn(true);

        boolean deleted = service.deleteBooking(3);

        assertTrue(deleted);
        verify(bookingRepository).deleteById(3);
    }

    @Test
    void testDeleteBooking_notExists_throws() {
        when(bookingRepository.existsById(999)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteBooking(999));
    }


}
