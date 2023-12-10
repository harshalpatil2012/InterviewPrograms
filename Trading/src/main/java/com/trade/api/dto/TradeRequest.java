package com.trade.api.dto;

        import com.trade.api.entity.Trade;
        import lombok.Data;

@Data
public class TradeRequest {

    private Long tradeId;
    private Long traderId;
    private String shareName;
    private int quantity;
    private double price;
    private BuyOrSell buyOrSell;
    private TradeStatus status;

    public Trade toTrade(TradeRequest request) {
        Trade dto = new Trade();
        dto.setShareName(request.getShareName());
        dto.setQuantity(request.getQuantity());
        dto.setPrice(request.getPrice());
        dto.setTraderId(request.getTraderId());
        dto.setBuyOrSell(BuyOrSell.valueOf(request.getBuyOrSell().name()));
        dto.setStatus(TradeStatus.valueOf(String.valueOf(request.getStatus())));
        return dto;
    }
}
