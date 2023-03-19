package pl.iwona.TaskProcessingConsumer.logic.exception;

public class AppRuntimeException extends RuntimeException {
    public AppRuntimeException(String message) {
        super(message);
    }
}