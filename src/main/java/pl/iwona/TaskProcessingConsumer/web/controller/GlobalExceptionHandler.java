package pl.iwona.TaskProcessingConsumer.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.iwona.TaskProcessingConsumer.domain.dto.error.ErrorResponseDTO;
import pl.iwona.TaskProcessingConsumer.logic.exception.AppRuntimeException;
import pl.iwona.TaskProcessingConsumer.logic.exception.ErrorCode;

/**
 * GlobalExceptionHandler Controller exception handler for all application exceptions.
 */
@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AppRuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppRuntimeException(final AppRuntimeException ex) {
        log.error("handleAppRuntimeException message: {}", ex.getMessage()
        );
        return new ResponseEntity<>(
                new ErrorResponseDTO(ex.getMessage()),
                HttpStatus.valueOf(ErrorCode.T004.getBusinessStatusCode()));
    }
}