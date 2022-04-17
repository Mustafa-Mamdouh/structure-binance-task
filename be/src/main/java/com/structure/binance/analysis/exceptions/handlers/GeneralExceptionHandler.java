package com.structure.binance.analysis.exceptions.handlers;

import com.structure.binance.analysis.dtos.responses.ServiceResponseDto;
import com.structure.binance.analysis.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ServiceResponseDto> handleGeneralException(final Exception internalServerException) {
        internalServerException.printStackTrace();
        ServiceResponseDto serviceResult = new ServiceResponseDto(internalServerException.getMessage(),500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serviceResult);
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<ServiceResponseDto> handleApplicationException(
            final ApplicationException applicationException) {
        return ResponseEntity.status(applicationException.getResponseCode()).
                body(ServiceResponseDto.builder().code(applicationException.getResponseCode())
                        .error(applicationException.getReason()).build());
    }
}
