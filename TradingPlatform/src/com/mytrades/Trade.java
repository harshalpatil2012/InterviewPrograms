package com.mytrades;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class Trade {
	private String shareName;
	private int shareQuantity;
	private double sharePrice;
	private String traderId;
	private String buyOrSell;
	private String tradeId;

	// Constructors, getters, setters...
}

public class BookingMicroservice {

	private static final ConcurrentHashMap<String, Trade> tradeStore = new ConcurrentHashMap<>();
	private static final AtomicLong tradeIdGenerator = new AtomicLong(1);

	public String bookTrade(Trade trade) {
		// Basic validation
		if (trade.getShareQuantity() <= 0 || trade.getSharePrice() <= 0) {
			return "Invalid trade details";
		}

		// Business logic for new trade
		if (trade.getTradeId() == null) {
			// Generate tradeId for new trade
			String generatedTradeId = "T" + tradeIdGenerator.getAndIncrement();
			trade.setTradeId(generatedTradeId);

			// Additional logic for Buy or Sell
			if ("Buy".equalsIgnoreCase(trade.getBuyOrSell())) {
				// Block amount in wallet (mocking logic)
				// Assume wallet is a separate service or system
				System.out.println("Blocking amount in wallet for " + trade.getTraderId());
			} else if ("Sell".equalsIgnoreCase(trade.getBuyOrSell())) {
				// Block share quantity in broker system (mocking logic)
				System.out.println("Blocking share quantity for " + trade.getTraderId());
			}

			// Send trade to Share Exchange (mocking logic)
			sendToShareExchange(trade);

			// Store the trade
			tradeStore.put(generatedTradeId, trade);

			return "Trade booked successfully with ID: " + generatedTradeId;
		}

		// Business logic for updating existing trade
		// ... (similar logic as new trade)

		return "Invalid operation";
	}

	private void sendToShareExchange(Trade trade) {
		// Mocking logic for sending trade to Share Exchange
		System.out.println("Sending trade to Share Exchange: " + trade);
	}

	public static void main(String[] args) {
		BookingMicroservice bookingMicroservice = new BookingMicroservice();

		// Example: New Trade
		Trade newTrade = new Trade("ABC", 100, 50.0, "Trader123", "Buy", null);
		String newTradeResult = bookingMicroservice.bookTrade(newTrade);
		System.out.println(newTradeResult);

		// Example: Update Trade
		Trade existingTrade = new Trade("XYZ", 50, 70.0, "Trader456", "Sell", "T1");
		String updateTradeResult = bookingMicroservice.bookTrade(existingTrade);
		System.out.println(updateTradeResult);
	}
}
