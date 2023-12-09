package com.trade.api.trade.exception;

public class InsufficientQuantityException extends RuntimeException {

    private final String shareName;
    private final int requestedQuantity;

    public InsufficientQuantityException(String shareName, int requestedQuantity) {
        super("Insufficient quantity for share " + shareName + ". Requested quantity: " + requestedQuantity);
        this.shareName = shareName;
        this.requestedQuantity = requestedQuantity;
    }

    public String getShareName() {
        return shareName;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}
