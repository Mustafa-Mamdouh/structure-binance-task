package com.structure.binance.analysis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BinanceMethods {
    SUBSCRIBE("SUBSCRIBE"),UN_SUBSCRIBE("UN_SUBSCRIBE");
    private String methodName;
}
