package pl.iwona.TaskProcessingConsumer.exception;

public class TaskNotExist extends RuntimeException {
    public TaskNotExist(String message) {
        super(message);
    }
}