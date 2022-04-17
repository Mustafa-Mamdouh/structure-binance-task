package com.structure.binance.analysis.dtos.responses;

import lombok.Data;

@Data
public class TradeInfoDto {
    private String symbol;
    private Double median;
    private Integer tradeCount = 0;
    private Double latestPrice;

}
