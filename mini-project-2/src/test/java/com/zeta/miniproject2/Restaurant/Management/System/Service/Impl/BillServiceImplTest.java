package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Bill;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Order;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BillStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.PaymentMethod;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.BillRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillServiceImplTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private BillServiceImpl billService;

    private Bill bill;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bill = Bill.builder()
                .billId(1)
                .totalAmount(500.0)
                .billStatus(BillStatus.OPEN)
                .createdTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateBill() {
        when(billRepository.save(any(Bill.class))).thenAnswer(i -> i.getArgument(0));

        Bill created = billService.createBill(bill);

        assertNotNull(created);
        assertEquals(BillStatus.OPEN, created.getBillStatus());
        assertNotNull(created.getCreatedTime());
        verify(billRepository, times(1)).save(bill);
    }

    @Test
    void testGetAllBills() {
        when(billRepository.findAll()).thenReturn(List.of(bill));

        List<Bill> bills = billService.getAllBills();

        assertEquals(1, bills.size());
        verify(billRepository, times(1)).findAll();
    }

    @Test
    void testGetBillById_Success() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));

        Bill result = billService.getBillById(1);

        assertEquals(1, result.getBillId());
        verify(billRepository, times(1)).findById(1);
    }

    @Test
    void testGetBillById_NotFound() {
        when(billRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> billService.getBillById(2));
        verify(billRepository, times(1)).findById(2);
    }

    @Test
    void testUpdateBill() {
        Bill updated = Bill.builder()
                .totalAmount(700.0)
                .billStatus(BillStatus.PENDING)
                .build();

        when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        when(billRepository.save(any(Bill.class))).thenAnswer(i -> i.getArgument(0));

        Bill result = billService.updateBill(1, updated);

        assertEquals(700.0, result.getTotalAmount());
        assertEquals(BillStatus.PENDING, result.getBillStatus());
        verify(billRepository).save(any(Bill.class));
    }

    @Test
    void testPatchBill() {
        Bill patch = Bill.builder()
                .totalAmount(800.0)
                .build();

        when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        when(billRepository.save(any(Bill.class))).thenAnswer(i -> i.getArgument(0));

        Bill result = billService.patchBill(1, patch);

        assertEquals(800.0, result.getTotalAmount());
        assertEquals(BillStatus.OPEN, result.getBillStatus()); // unchanged
        verify(billRepository).save(any(Bill.class));
    }

    @Test
    void testPayBill() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        when(billRepository.save(any(Bill.class))).thenAnswer(i -> i.getArgument(0));

        Bill paid = billService.payBill(1, PaymentMethod.CASH);

        assertEquals(BillStatus.PAID, paid.getBillStatus());
        assertEquals(PaymentMethod.CASH, paid.getPaymentMethod());
        assertNotNull(paid.getPaidTime());
        verify(billRepository).save(any(Bill.class));
    }

    @Test
    void testDeleteBill_Success() {
        when(billRepository.existsById(1)).thenReturn(true);

        boolean result = billService.deleteBill(1);

        assertTrue(result);
        verify(billRepository).deleteById(1);
    }

    @Test
    void testDeleteBill_NotFound() {
        when(billRepository.existsById(2)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> billService.deleteBill(2));
        verify(billRepository, never()).deleteById(2);
    }

    @Test
    void testAssignOrdersToBill() {
        Order o1 = Order.builder().orderId(1).build();
        Order o2 = Order.builder().orderId(2).build();

        when(orderService.getOrderById(1)).thenReturn(o1);
        when(orderService.getOrderById(2)).thenReturn(o2);

        billService.assignOrdersToBill(bill, List.of(1, 2));

        assertEquals(2, bill.getOrders().size());
        verify(orderService, times(1)).getOrderById(1);
        verify(orderService, times(1)).getOrderById(2);
    }

    @Test
    void testAssignOrdersToBill_EmptyList() {
        billService.assignOrdersToBill(bill, List.of());

        assertTrue(bill.getOrders().isEmpty());
        verifyNoInteractions(orderService);
    }
}
