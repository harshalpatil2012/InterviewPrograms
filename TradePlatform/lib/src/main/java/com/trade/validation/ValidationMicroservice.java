package com.trade.validation;

import java.util.concurrent.ConcurrentHashMap;


public class ValidationMicroservice {

    private static final ConcurrentHashMap<String, Double> walletBalances = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> shareQuantities = new ConcurrentHashMap<>();

    public ValidationResponse validateTrade(Trade trade) {
        // Basic validation
        if (trade.getShareQuantity() <= 0 || trade.getSharePrice() <= 0) {
            return new ValidationResponse(false, "Invalid trade details");
        }

        // Additional validation for Buy or Sell
        if ("Buy".equalsIgnoreCase(trade.getBuyOrSell())) {
            // Check wallet balance (mocking logic)
            double walletBalance = walletBalances.getOrDefault(trade.getTraderId(), 0.0);
            if (walletBalance < trade.getShareQuantity() * trade.getSharePrice()) {
                return new ValidationResponse(false, "Insufficient funds in wallet");
            }
        } else if ("Sell".equalsIgnoreCase(trade.getBuyOrSell())) {
            // Check share quantity in broker system (mocking logic)
            int availableShares = shareQuantities.getOrDefault(trade.getTraderId(), 0);
            if (availableShares < trade.getShareQuantity()) {
                return new ValidationResponse(false, "Insufficient shares in broker system");
            }
        }

        return new ValidationResponse(true, "Trade validation successful");
    }

    public static void main(String[] args) {
        ValidationMicroservice validationMicroservice = new ValidationMicroservice();

        // Example: Valid Buy Trade
        Trade buyTrade = new Trade("ABC", 100, 50.0, "Trader123", "Buy", null);
        ValidationResponse buyTradeValidation = validationMicroservice.validateTrade(buyTrade);
        System.out.println("Buy Trade Validation Result: " + buyTradeValidation.getMessage());

        // Example: Invalid Sell Trade (insufficient shares)
        Trade sellTrade = new Trade("XYZ", 150, 70.0, "Trader456", "Sell", null);
        ValidationResponse sellTradeValidation = validationMicroservice.validateTrade(sellTrade);
        System.out.println("Sell Trade Validation Result: " + sellTradeValidation.getMessage());
    }
}
