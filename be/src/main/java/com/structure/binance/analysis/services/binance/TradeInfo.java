package com.structure.binance.analysis.services.binance;

import com.structure.binance.analysis.services.helpers.MedianCalculator;
import lombok.Getter;

@Getter
public class TradeInfo {
    private final String symbol;
    private Integer tradeCount = 0;
    private Double latestPrice;
    private MedianCalculator medianCalculator;

    public TradeInfo(String symbol) {
        this.symbol = symbol;
        medianCalculator = new MedianCalculator();

    }

    public void addPrice(Double price) {
        tradeCount++;
        latestPrice = price;
        medianCalculator.add(price);
    }

    public double getMedian() {
        return medianCalculator.getMedian();
    }
}
