package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.BookingDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Booking;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Service.BookingService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.CustomerService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.RestaurantTableService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.BookingMapper;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RestaurantTableService restaurantTableService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody Booking booking) {
        log.info("API Request - Create booking: {}", booking);
        return new ResponseEntity<>(
                BookingMapper.toDTO(bookingService.createBooking(booking)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        log.info("API Request - Get all bookings");
        return ResponseEntity.ok(
                bookingService.getAllBookings()
                        .stream()
                        .map(BookingMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Integer id) {
        log.info("API Request - Get booking ID: {}", id);
        return ResponseEntity.ok(
                BookingMapper.toDTO(bookingService.getBookingById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Integer id, @RequestBody Booking booking) {
        log.info("API Request - Update booking ID: {}", id);
        return ResponseEntity.ok(
                BookingMapper.toDTO(bookingService.updateBooking(id, booking))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingDTO> patchBooking(@PathVariable Integer id, @RequestBody BookingDTO bookingDTO) {
        log.info("API Request - Patch booking ID: {}", id);
        Booking existing = bookingService.getBookingById(id);

        Customer customer = null;
        RestaurantTable table = null;
        if(bookingDTO.getCustomerId()!=null)
            customer = customerService.getCustomerById(bookingDTO.getCustomerId());
        if(bookingDTO.getTableId()!=null)
            table = restaurantTableService.getTableById(bookingDTO.getTableId());
        Booking booking = BookingMapper.toEntity(bookingDTO,customer,table);
        existing = EntityUtil.copyNonNullProperties(booking, existing);
        return ResponseEntity.ok(
                BookingMapper.toDTO(bookingService.patchBooking(id, existing))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        log.info("API Request - Delete booking ID: {}", id);
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
