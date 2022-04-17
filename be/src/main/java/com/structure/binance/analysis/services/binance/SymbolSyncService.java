package com.structure.binance.analysis.services.binance;

import com.structure.binance.analysis.dtos.binance.SymbolDto;
import com.structure.binance.analysis.dtos.binance.SymbolResponse;
import com.structure.binance.analysis.dtos.internal.events.SymbolUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@DependsOn({"binanceService"})
public class SymbolSyncService {
    @Qualifier("binanceRestTemplate")
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    SymbolResponse latestSymbolResponse;

    //TODO: enable scheduling to pull changes
    @PostConstruct
    private void getAvailableSymbols() {
        ResponseEntity<SymbolResponse> symbolResponse = restTemplate.getForEntity("exchangeInfo", SymbolResponse.class);
        SymbolResponse responseBody = symbolResponse.getBody();
        if (Objects.isNull(latestSymbolResponse)) {
            latestSymbolResponse = responseBody;
            publishInternalEvent(latestSymbolResponse.getSymbols());
        } else {
            Set<SymbolDto> tempSet = new HashSet<>(responseBody.getSymbols());
            tempSet.removeAll(latestSymbolResponse.getSymbols());
            if (tempSet.size() > 0) {
                //new item pulled
                publishInternalEvent(tempSet);
            }
            latestSymbolResponse = responseBody;
        }
    }

    private void publishInternalEvent(Set<SymbolDto> symbolSet) {
        applicationEventPublisher.publishEvent(SymbolUpdatedEvent.builder().source(this).symbols(new HashSet<>(symbolSet)).build());
    }
}
