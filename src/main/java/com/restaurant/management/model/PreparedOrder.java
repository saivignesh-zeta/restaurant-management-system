package com.restaurant.management.model;

public class PreparedOrder {

    private int preparedId;
    private int orderId;
    private int itemId;
    private String status;

    public PreparedOrder() {}

    public PreparedOrder(int preparedId, int orderId, int itemId, String status) {
        this.preparedId = preparedId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.status = status;
    }

    public int getPreparedId() {
        return preparedId;
    }

    public void setPreparedId(int preparedId) {
        this.preparedId = preparedId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
