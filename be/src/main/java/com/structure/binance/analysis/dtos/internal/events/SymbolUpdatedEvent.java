package com.structure.binance.analysis.dtos.internal.events;

import com.structure.binance.analysis.dtos.binance.SymbolDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

@Getter
public class SymbolUpdatedEvent extends ApplicationEvent {
    private Set<SymbolDto> symbols;

    @Builder
    public SymbolUpdatedEvent(Object source, Set<SymbolDto> symbols) {
        super(source);
        this.symbols = symbols;
    }
}
