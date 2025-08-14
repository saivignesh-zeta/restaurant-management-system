package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.BillDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Bill;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BillStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.PaymentMethod;
import com.zeta.miniproject2.Restaurant.Management.System.Service.BillService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.BillMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillControllerTest {

    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    private Bill bill1;
    private Bill bill2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bill1 = Bill.builder()
                .billId(1)
                .totalAmount(500.0)
                .billStatus(BillStatus.OPEN)
                .createdTime(LocalDateTime.now())
                .build();

        bill2 = Bill.builder()
                .billId(2)
                .totalAmount(1000.0)
                .billStatus(BillStatus.OPEN)
                .createdTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateBill() {
        when(billService.createBill(any(Bill.class))).thenReturn(bill1);

        ResponseEntity<BillDTO> response = billController.createBill(bill1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBillId());

        verify(billService, times(1)).createBill(bill1);
    }

    @Test
    void testGetAllBills() {
        when(billService.getAllBills()).thenReturn(List.of(bill1, bill2));

        ResponseEntity<List<BillDTO>> response = billController.getAllBills();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        verify(billService, times(1)).getAllBills();
    }

    @Test
    void testGetBillById() {
        when(billService.getBillById(1)).thenReturn(bill1);

        ResponseEntity<BillDTO> response = billController.getBillById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getBillId());

        verify(billService, times(1)).getBillById(1);
    }

    @Test
    void testUpdateBill() {
        Bill updated = Bill.builder()
                .totalAmount(600.0)
                .billStatus(BillStatus.PENDING)
                .build();

        when(billService.updateBill(eq(1), any(Bill.class))).thenReturn(updated);

        ResponseEntity<BillDTO> response = billController.updateBill(1, updated);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BillStatus.PENDING, response.getBody().getBillStatus());

        verify(billService, times(1)).updateBill(1, updated);
    }

    @Test
    void testPatchBill() {
        BillDTO patchDTO = BillDTO.builder()
                .totalAmount(700.0)
                .build();

        Bill patchedBill = Bill.builder()
                .billId(1)
                .totalAmount(700.0)
                .billStatus(BillStatus.OPEN)
                .createdTime(LocalDateTime.now())
                .build();

        when(billService.getBillById(1)).thenReturn(bill1);
        when(billService.patchBill(eq(1), any(Bill.class))).thenReturn(patchedBill);

        ResponseEntity<BillDTO> response = billController.patchBill(1, patchDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(700.0, response.getBody().getTotalAmount());

        verify(billService, times(1)).getBillById(1);
        verify(billService, times(1)).patchBill(eq(1), any(Bill.class));
    }

    @Test
    void testPayBill() {
        Bill paidBill = Bill.builder()
                .billId(1)
                .billStatus(BillStatus.PAID)
                .paymentMethod(PaymentMethod.CASH)
                .build();

        when(billService.payBill(1, PaymentMethod.CASH)).thenReturn(paidBill);

        ResponseEntity<BillDTO> response = billController.payBill(1, PaymentMethod.CASH);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BillStatus.PAID, response.getBody().getBillStatus());
        assertEquals(PaymentMethod.CASH, response.getBody().getPaymentMethod());

        verify(billService, times(1)).payBill(1, PaymentMethod.CASH);
    }

    @Test
    void testDeleteBill() {
        when(billService.deleteBill(1)).thenReturn(true);

        ResponseEntity<Void> response = billController.deleteBill(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(billService, times(1)).deleteBill(1);
    }
}
