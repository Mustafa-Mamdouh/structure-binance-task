package com.structure.binance.analysis.dtos.binance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceEvent{
    @JsonProperty("e")
    String event;
    @JsonProperty("E")
    Date timestamp;
    @JsonProperty("s")
    String symbol;
    @JsonProperty("p")
    Double price;
    @JsonProperty("q")
    Double quantity;
    String result;
    Integer id;
}
