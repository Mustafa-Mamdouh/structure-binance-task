package com.structure.binance.analysis.dtos.internal.events;

import com.structure.binance.analysis.dtos.binance.BinanceEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TradeEvent extends ApplicationEvent {
    private BinanceEvent event;

    @Builder
    public TradeEvent(Object source, BinanceEvent event) {
        super(source);
        this.event = event;
    }
}
