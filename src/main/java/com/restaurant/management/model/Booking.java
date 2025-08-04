package com.restaurant.management.model;

import java.time.LocalDateTime;

public class Booking {

    private int bookingId;
    private int customerId;
    private int tableId;
    private LocalDateTime bookingTime;
    private String status;

    public Booking() {}

    public Booking(int bookingId, int customerId, int tableId, LocalDateTime bookingTime, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.tableId = tableId;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
