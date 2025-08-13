package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.BillDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Bill;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.PaymentMethod;
import com.zeta.miniproject2.Restaurant.Management.System.Service.BillService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.BillMapper;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
@Slf4j
public class BillController {

    private final BillService billService;

    @PostMapping
    public ResponseEntity<BillDTO> createBill(@RequestBody Bill bill) {
        log.info("API Request - Create bill");
        return new ResponseEntity<>(
                BillMapper.toDTO(billService.createBill(bill)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<BillDTO>> getAllBills() {
        log.info("API Request - Get all bills");
        return ResponseEntity.ok(
                billService.getAllBills().stream()
                        .map(BillMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDTO> getBillById(@PathVariable Integer id) {
        log.info("API Request - Get bill ID: {}", id);
        return ResponseEntity.ok(
                BillMapper.toDTO(billService.getBillById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDTO> updateBill(@PathVariable Integer id, @RequestBody Bill bill) {
        log.info("API Request - Update bill ID: {}", id);
        return ResponseEntity.ok(
                BillMapper.toDTO(billService.updateBill(id, bill))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BillDTO> patchBill(@PathVariable Integer id, @RequestBody BillDTO billDTO) {
        log.info("API Request - Patch bill ID: {}", id);
        Bill existing = billService.getBillById(id);
        Bill bill = BillMapper.toEntity(billDTO);
        if(billDTO.getOrderIds()!=null)
            billService.assignOrdersToBill(bill,billDTO.getOrderIds());
        existing = EntityUtil.copyNonNullProperties(bill, existing);
        return ResponseEntity.ok(
                BillMapper.toDTO(billService.patchBill(id, existing))
        );
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<BillDTO> payBill(
            @PathVariable Integer id,
            @RequestParam PaymentMethod paymentMethod
    ) {
        log.info("API Request - Pay bill ID: {}", id);
        return ResponseEntity.ok(
                BillMapper.toDTO(billService.payBill(id, paymentMethod))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Integer id) {
        log.info("API Request - Delete bill ID: {}", id);
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}
