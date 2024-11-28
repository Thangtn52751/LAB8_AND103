package dev.md19303.lab8.Model;

public class OrderResponse {
    private String orderId;
    private String status;

    public OrderResponse(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }


    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
