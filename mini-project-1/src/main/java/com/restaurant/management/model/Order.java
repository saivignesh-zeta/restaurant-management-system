package com.restaurant.management.model;

import java.time.LocalDateTime;

public class Order {

    private int orderId;
    private int tableId;
    private int waiterId;
    private LocalDateTime orderTime;
    private String status; // Pending, Completed, Cancelled

    public Order() {}

    public Order(int orderId, int tableId, int waiterId, LocalDateTime orderTime, String status) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.waiterId = waiterId;
        this.orderTime = orderTime;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }


}
