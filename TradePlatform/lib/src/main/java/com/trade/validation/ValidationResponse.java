package com.trade.validation;

@Data
public class ValidationResponse {
    private boolean isValid;
    private String message;

    public ValidationResponse(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }
}
