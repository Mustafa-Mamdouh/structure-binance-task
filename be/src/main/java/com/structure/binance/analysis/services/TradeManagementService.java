package com.structure.binance.analysis.services;

import com.structure.binance.analysis.dtos.binance.BinanceEvent;
import com.structure.binance.analysis.dtos.internal.events.TradeEvent;
import com.structure.binance.analysis.exceptions.ApplicationException;
import com.structure.binance.analysis.mappers.TradeInfoMapper;
import com.structure.binance.analysis.services.binance.TradeInfo;
import com.structure.binance.analysis.utils.SimpleHashMap;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TradeManagementService implements ApplicationListener<TradeEvent> {
    private final TradeInfoMapper mapper = Mappers.getMapper(TradeInfoMapper.class);

    private Map<String, TradeInfo> tradesMap = new ConcurrentHashMap<>();
    private SimpleHashMap<String, TradeInfo> tradesMap2 = new SimpleHashMap<>(2500);

    @Override
    public void onApplicationEvent(TradeEvent event) {
        BinanceEvent binanceEvent = event.getEvent();
        TradeInfo tradeInfo = tradesMap.getOrDefault(binanceEvent.getSymbol(), new TradeInfo(binanceEvent.getSymbol()));
        tradeInfo.addPrice(binanceEvent.getPrice());
        tradesMap.put(binanceEvent.getSymbol(), tradeInfo);
        tradesMap2.put(binanceEvent.getSymbol(), tradeInfo);
    }


    public Object getSymbolInfo(String symbol) {
        if ("symbols".equalsIgnoreCase(symbol)) {
            return tradesMap.values().stream().map(mapper::asDto).collect(Collectors.toList());
        } else if (tradesMap.containsKey(symbol)) {
            return mapper.asDto(tradesMap.get(symbol));
        } else {
            return new ApplicationException(HttpStatus.BAD_REQUEST.value(), String.format("Symbol [%s] not found!", symbol));
        }
    }

    public Object getSymbolInfo2(String symbol) {
        if ("symbols".equals(symbol)) {
            return tradesMap2.values().stream().map(mapper::asDto).collect(Collectors.toList());
        } else if (tradesMap.containsKey(symbol)) {
            return mapper.asDto(tradesMap2.get(symbol));
        } else {
            return new ApplicationException(HttpStatus.BAD_REQUEST.value(), String.format("Symbol [%s] not found!", symbol));
        }
    }
}
