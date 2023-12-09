package com.trade.api.reporting;


import com.trade.api.entity.Trade;
import com.trade.api.trade.jpa.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableElasticsearchRepositories
public class ReportingServiceApplication {

    private final TradeRepository tradeRepository;

    @Autowired
    public ReportingServiceApplication(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/trades/{tradeId}")
    public Optional<Trade> getTrade(@PathVariable Long tradeId) {
        return tradeRepository.findByTradeId(tradeId);
    }

    @GetMapping("/trades/search")
    public List<Trade> searchTrades(@RequestParam String keyword) {
        return tradeRepository.findByShareNameContaining(keyword);
    }
}
