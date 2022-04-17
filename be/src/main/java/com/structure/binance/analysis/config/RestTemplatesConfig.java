package com.structure.binance.analysis.config;

import com.structure.binance.analysis.exceptions.handlers.BinanceErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplatesConfig {
    @Value("${binance.base.url}")
    private String binanceBaseUrl;

    @Bean("binanceRestTemplate")
    public RestTemplate binanceRestTemplate(){
        return new RestTemplateBuilder()
                .uriTemplateHandler(new DefaultUriBuilderFactory(binanceBaseUrl))
                .errorHandler(new BinanceErrorHandler())
                .build();
    }
}
