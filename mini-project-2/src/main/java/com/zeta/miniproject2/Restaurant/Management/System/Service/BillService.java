package com.zeta.miniproject2.Restaurant.Management.System.Service;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Bill;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.PaymentMethod;

import java.util.List;

public interface BillService {
    Bill createBill(Bill bill);

    List<Bill> getAllBills();

    Bill getBillById(Integer billId);

    Bill updateBill(Integer billId, Bill updatedBill);

    Bill patchBill(Integer billId, Bill updatedBill);

    Bill payBill(Integer billId, PaymentMethod paymentMethod);

    boolean deleteBill(Integer billId);

    public void assignOrdersToBill(Bill bill, List<Integer> orderIds);
}
