package pl.iwona.TaskProcessingConsumer.domain;

public enum TaskStatus {
    CLOSE("90%-100%"),
    OPEN("0%-10%"),
    IN_PROGRESS("11%-89%");

    private final String statusInPercentage;

    TaskStatus(String statusInPercentage) {
        this.statusInPercentage = statusInPercentage;
    }

    public String getStatusInPercentage() {
        return statusInPercentage;
    }
}
