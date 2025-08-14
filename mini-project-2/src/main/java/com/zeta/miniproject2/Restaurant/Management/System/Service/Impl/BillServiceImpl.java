package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Bill;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Order;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.BillStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.PaymentMethod;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.BillRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.BillService;
import com.zeta.miniproject2.Restaurant.Management.System.Service.OrderService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final OrderService orderService;
    @Override
    public Bill createBill(Bill bill) {
        log.info("Creating new bill: {}", bill);
        bill.setBillStatus(BillStatus.OPEN);
        bill.setCreatedTime(LocalDateTime.now());
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getAllBills() {
        log.info("Fetching all bills");
        return billRepository.findAll();
    }

    @Override
    public Bill getBillById(Integer billId) {
        log.info("Fetching bill with ID: {}", billId);
        return billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + billId));
    }

    @Override
    public Bill updateBill(Integer billId, Bill updatedBill){
        log.info("Fully updating bill with ID: {}", billId);

        Bill existingBill = getBillById(billId);

        BeanUtils.copyProperties(updatedBill, existingBill, "billId");

        Bill savedBill = billRepository.save(existingBill);

        log.info("Bill with ID {} successfully fully updated", billId);

        return savedBill;
    }

    @Override
    public Bill patchBill(Integer billId, Bill updatedBill) {
        log.info("Updating bill with ID: {}", billId);
        Bill existing = getBillById(billId);
        existing = EntityUtil.copyNonNullProperties(updatedBill, existing);
        return billRepository.save(existing);
    }

    @Override
    public Bill payBill(Integer billId, PaymentMethod paymentMethod) {
        log.info("Paying bill with ID: {}", billId);
        Bill bill = getBillById(billId);
        bill.setPaymentMethod(paymentMethod);
        bill.setBillStatus(BillStatus.PAID);
        bill.setPaidTime(LocalDateTime.now());
        return billRepository.save(bill);
    }

    @Override
    public boolean deleteBill(Integer billId) {
        log.info("Deleting bill with ID: {}", billId);
        if (!billRepository.existsById(billId)) {
            throw new ResourceNotFoundException("Bill not found with ID: " + billId);
        }
        billRepository.deleteById(billId);
        return true;
    }

    @Override
    public void assignOrdersToBill(Bill bill, List<Integer> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            bill.setOrders(Collections.emptyList());
            return;
        }
        List<Order> orders = orderIds.stream()
                .map(orderService::getOrderById) // or orderRepository.findById(id).orElseThrow(...)
                .collect(Collectors.toList());

        bill.setOrders(orders);
    }

}
