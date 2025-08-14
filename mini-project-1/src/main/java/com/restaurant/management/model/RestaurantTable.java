package com.restaurant.management.model;

public class RestaurantTable {

    private int tableId;
    private int capacity;
    private String status;

    public RestaurantTable() {}

    public RestaurantTable(int tableId, int capacity, String status) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.status = status;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
