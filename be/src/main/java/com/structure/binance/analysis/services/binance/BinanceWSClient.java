package com.structure.binance.analysis.services.binance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.structure.binance.analysis.dtos.binance.BinanceEvent;
import com.structure.binance.analysis.dtos.internal.events.TradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.ApplicationEventPublisher;

import java.net.URI;

@Slf4j
public class BinanceWSClient extends WebSocketClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ApplicationEventPublisher applicationEventPublisher;
     public String identifier;

    public BinanceWSClient(String url, ApplicationEventPublisher applicationEventPublisher, String identifier) {
        super(URI.create(url));
        this.applicationEventPublisher = applicationEventPublisher;
        this.identifier = identifier;
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("Client {}, Connection to binance WS established!",identifier);
    }

    @Override
    public void onMessage(String s) {
        log.debug("Receiving message.");
        try {
            BinanceEvent binanceEvent = objectMapper.readValue(s, BinanceEvent.class);
            if ("trade".equals(binanceEvent.getEvent())) {
                applicationEventPublisher.publishEvent(TradeEvent.builder().event(binanceEvent).source(this).build());
            } else {
                System.out.println(">>>" + s);
            }
        } catch (JsonProcessingException e) {
            log.error("Client {}, Message parsing error, Message: {}.",identifier, s);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.error("Client {},Connection closed reason {} frame {} closed by host: .",identifier, s, i, b);

    }

    @Override
    public void onError(Exception e) {
        log.error("Client {},Connection error, Error Message: {}.",identifier,e.getMessage());
        e.printStackTrace();
    }

}
