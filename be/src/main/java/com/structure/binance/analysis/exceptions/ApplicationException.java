package com.structure.binance.analysis.exceptions;

import lombok.Data;

import java.util.function.Supplier;

@Data
public class ApplicationException extends RuntimeException implements Supplier<ApplicationException> {
    private static final long serialVersionUID = -184305369725661529L;
    private Integer responseCode;
    private String reason;

    public ApplicationException(Integer responseCode, String reason) {
        this.responseCode = responseCode;
        this.reason = reason;
    }

    @Override
    public ApplicationException get() {
        return this;
    }
}
