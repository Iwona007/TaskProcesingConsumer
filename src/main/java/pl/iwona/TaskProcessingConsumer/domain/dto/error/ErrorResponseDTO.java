package pl.iwona.TaskProcessingConsumer.domain.dto.error;

import lombok.Getter;
import lombok.Setter;

/**
 * General ErrorResponseDTO for application
 */
@Getter
@Setter
public class ErrorResponseDTO {

    private String message;

    public ErrorResponseDTO( String message) {
        this.message = message;
    }
}