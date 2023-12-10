package com.trade.rest.controller;

import com.trade.api.dto.TradeRequest;
import com.trade.api.entity.Trade;
import com.trade.api.trade.jpa.TradeRepository;
import com.trade.api.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trades")
public class TradingController {

    private final TradeService tradeService;
    private final TradeRepository tradeRepository;

    @Autowired
    public TradingController(TradeService tradeService, TradeRepository tradeRepository) {
        this.tradeService = tradeService;
        this.tradeRepository = tradeRepository;
    }

    @PostMapping("/submit")
    public void submitTrade(@RequestBody TradeRequest request) {
        tradeService.submitTrade(request);
    }

    @PutMapping("/update")
    public void updateTrade(@RequestBody TradeRequest update) {
        tradeService.updateTrade(update);
    }

    @DeleteMapping("/cancel/{tradeId}")
    public void cancelTrade(@PathVariable Long tradeId) {
        tradeService.cancelTrade(tradeId);
    }

    @GetMapping("/{tradeId}")
    public Optional<Trade> getTrade(@PathVariable Long tradeId) {
        return tradeRepository.findByTradeId(tradeId);
    }

    @GetMapping("/search")
    public List<Trade> searchTrades(@RequestParam String shareName) {
        return tradeRepository.findByShareNameContaining(shareName);
    }
}