package com.structure.binance.analysis.services.binance;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;

@Builder
@Slf4j
public class SendingTask implements Runnable {
    private WebSocket connection;
    private Object request;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    @Override
    public void run() {
        log.info("sending request.");
        String requestString = objectMapper.writeValueAsString(request);
        log.info("request string {}.",requestString);
        connection.send(requestString);
    }
}
