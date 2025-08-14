package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.BookingDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BookingStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Service.BookingService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.CustomerService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.RestaurantTableService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.BookingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private CustomerService customerService;

    @Mock
    private RestaurantTableService restaurantTableService;

    @InjectMocks
    private BookingController controller;

    private Booking booking;
    private BookingDTO bookingDTO;
    private Customer customer;
    private RestaurantTable table;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = Customer.builder()
                .customerId(1)
                .name("John")
                .contact("1234567890")
                .build();

        table = RestaurantTable.builder()
                .tableId(10)
                .capacity(4)
                .build();

        booking = Booking.builder()
                .bookingId(100)
                .customer(customer)
                .table(table)
                .bookingTime(LocalDateTime.now())
                .status(BookingStatus.PENDING)
                .build();

        bookingDTO = BookingMapper.toDTO(booking);
    }

    @Test
    void testCreateBooking() {
        when(bookingService.createBooking(booking)).thenReturn(booking);

        ResponseEntity<BookingDTO> response = controller.createBooking(booking);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(bookingDTO.getBookingId(), response.getBody().getBookingId());
        verify(bookingService, times(1)).createBooking(booking);
    }

    @Test
    void testGetAllBookings() {
        when(bookingService.getAllBookings()).thenReturn(List.of(booking));

        ResponseEntity<List<BookingDTO>> response = controller.getAllBookings();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(bookingDTO.getBookingId(), response.getBody().get(0).getBookingId());
        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void testGetBookingById() {
        when(bookingService.getBookingById(100)).thenReturn(booking);

        ResponseEntity<BookingDTO> response = controller.getBookingById(100);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookingDTO.getBookingId(), response.getBody().getBookingId());
        verify(bookingService, times(1)).getBookingById(100);
    }

    @Test
    void testUpdateBooking() {
        when(bookingService.updateBooking(100, booking)).thenReturn(booking);

        ResponseEntity<BookingDTO> response = controller.updateBooking(100, booking);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookingDTO.getBookingId(), response.getBody().getBookingId());
        verify(bookingService, times(1)).updateBooking(100, booking);
    }

    @Test
    void testPatchBooking() {
        BookingDTO patchDTO = BookingDTO.builder()
                .status(BookingStatus.CONFIRMED)
                .customerId(1)
                .tableId(10)
                .build();

        when(bookingService.getBookingById(100)).thenReturn(booking);
        when(customerService.getCustomerById(1)).thenReturn(customer);
        when(restaurantTableService.getTableById(10)).thenReturn(table);
        when(bookingService.patchBooking(eq(100), any(Booking.class))).thenReturn(booking);

        ResponseEntity<BookingDTO> response = controller.patchBooking(100, patchDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookingDTO.getBookingId(), response.getBody().getBookingId());

        verify(bookingService, times(1)).getBookingById(100);
        verify(customerService, times(1)).getCustomerById(1);
        verify(restaurantTableService, times(1)).getTableById(10);
        verify(bookingService, times(1)).patchBooking(eq(100), any(Booking.class));
    }

    @Test
    void testDeleteBooking() {
        when(bookingService.deleteBooking(100)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteBooking(100);

        assertEquals(204, response.getStatusCodeValue());
        verify(bookingService, times(1)).deleteBooking(100);
    }

}
