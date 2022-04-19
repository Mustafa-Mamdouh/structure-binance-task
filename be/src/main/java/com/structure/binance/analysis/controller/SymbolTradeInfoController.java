package com.structure.binance.analysis.controller;

import com.structure.binance.analysis.services.TradeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
@RequiredArgsConstructor
public class SymbolTradeInfoController {
    private final TradeManagementService tradeManagementService;

    @GetMapping(value = "/{symbol}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllSymbolsInfo(@PathVariable("symbol") String symbol) {
        return ResponseEntity.ok(tradeManagementService.getSymbolInfo(symbol));
    }

    @GetMapping(value = "new/{symbol}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllSymbolsInfo2(@PathVariable("symbol") String symbol) {
        return ResponseEntity.ok(tradeManagementService.getSymbolInfo2(symbol));
    }
}
