package com.structure.binance.analysis.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BinanceErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) {
        String message = getErrorMessage(getResponseBody(response), getCharset(response));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Binance call Error: %s", message));
    }

    private String getErrorMessage(byte[] responseBody, Charset charset) {
        if (responseBody.length == 0) {
            return "[no body]";
        }

        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        return new String(responseBody, charset);
    }
}
