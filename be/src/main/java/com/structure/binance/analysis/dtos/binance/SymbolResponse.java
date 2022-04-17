package com.structure.binance.analysis.dtos.binance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolResponse {
    String timezone;
    Date serverTime;
    Set<SymbolDto> symbols = new HashSet<>();
}
