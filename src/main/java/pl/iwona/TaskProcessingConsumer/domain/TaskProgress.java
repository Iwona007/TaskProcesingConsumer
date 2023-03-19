package pl.iwona.TaskProcessingConsumer.domain;

public enum TaskProgress {
    ZERO ("0%"),
    TEN ("10%"),
    FIFTY("50%"),
    ONE_HUNDRED ("100%");
public final String percentage;

    TaskProgress(String percentage) {
        this.percentage = percentage;
    }

    public String getPercentage() {
       return this.percentage;
    }
}
