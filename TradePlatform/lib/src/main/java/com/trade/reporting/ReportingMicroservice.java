package com.trade.reporting;

import java.util.concurrent.ConcurrentHashMap;

import com.trade.booking.Trade;

public class ReportingMicroservice {

	private static final ConcurrentHashMap<String, Trade> tradeStore = new ConcurrentHashMap<>();

	public String getTradeDetails(String tradeId) {
		// Retrieve trade details from the store
		Trade trade = tradeStore.get(tradeId);

		if (trade != null) {
			return "Trade Details: " + trade.toString();
		} else {
			return "Trade not found with ID: " + tradeId;
		}
	}

	public static void main(String[] args) {
		ReportingMicroservice reportingMicroservice = new ReportingMicroservice();

		// Example: Get Trade Details
		String tradeId = "T1";
		String tradeDetails = reportingMicroservice.getTradeDetails(tradeId);
		System.out.println(tradeDetails);
	}
}
