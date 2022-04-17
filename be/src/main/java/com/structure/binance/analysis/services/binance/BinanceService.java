package com.structure.binance.analysis.services.binance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.structure.binance.analysis.BinanceMethods;
import com.structure.binance.analysis.dtos.binance.requests.BinanceWSRequest;
import com.structure.binance.analysis.dtos.internal.events.SymbolUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinanceService implements ApplicationListener<SymbolUpdatedEvent> {
    @Value("${binance.subscribe.symbol.batch.size}")
    private Integer BATCH_SIZE;
    @Value("${binance.ws.url}")
    private String binanceUrl;
    @Value("${binance.stream.subscribe.limit}")
    private int binanceSteamSubscribeLimit;

    private final ApplicationEventPublisher applicationEventPublisher;

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    private List<BinanceWSClient> binanceWSClientList = new ArrayList<>();
    private int requestId = 0;
    private int currentClientSymbols = 0;

    @PostConstruct
    void connect() {
        addNewClientToPool();
    }

    private BinanceWSClient addNewClientToPool() {
        BinanceWSClient newClient = new BinanceWSClient(binanceUrl, applicationEventPublisher, String.format("Client-%d", binanceWSClientList.size()));
        binanceWSClientList.add(newClient);
        connectLatestClient();
        currentClientSymbols = 0;
        return newClient;
    }

    private void connectLatestClient() {
        if (!binanceWSClientList.isEmpty() && !binanceWSClientList.get(binanceWSClientList.size() - 1).isOpen()) {
            binanceWSClientList.get(binanceWSClientList.size() - 1).connect();
        }
    }

    private BinanceWSClient getCurrentClient() {
        return binanceWSClientList.get(binanceWSClientList.size() - 1);
    }

    @Override
    public void onApplicationEvent(SymbolUpdatedEvent event) {
        try {
            this.subscribeToTrades(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToTrades(SymbolUpdatedEvent event) throws JsonProcessingException {
        Queue<String> symbolQ = new LinkedBlockingQueue<>();
        symbolQ.addAll(event.getSymbols().stream().map(symbolDto -> String.format("%s@trade", symbolDto.getSymbol().toLowerCase())).collect(Collectors.toList()));
        ArrayList<String> symbolBatch = new ArrayList<>(BATCH_SIZE);
        int initialDelay = 500;
        while (!symbolQ.isEmpty()) {
            symbolBatch.add(symbolQ.poll());
            if (symbolBatch.size() == BATCH_SIZE) {
                sendRequest(new ArrayList<>(symbolBatch), initialDelay);
                symbolBatch.clear();
                initialDelay += 500;
            }
        }
        if (!symbolBatch.isEmpty()) {
            sendRequest(new ArrayList<>(symbolBatch), initialDelay);
        }

    }

    private void sendRequest(List<String> symbolBatch, int initialDelay) {
        if (symbolBatch.size() > 0) {
            if (currentClientSymbols + symbolBatch.size() > binanceSteamSubscribeLimit) {
                addNewClientToPool();
            }
            currentClientSymbols += symbolBatch.size();
            BinanceWSRequest subscribeRequest = BinanceWSRequest.builder().id(++requestId).params(symbolBatch).method(BinanceMethods.SUBSCRIBE.getMethodName()).build();
            executor.schedule(SendingTask.builder().connection(getCurrentClient().getConnection()).request(subscribeRequest).build(), initialDelay, TimeUnit.MILLISECONDS);
        }
    }
}
