package com.structure.binance.analysis.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponseDto {
    private String error;
    private Integer code;
}
