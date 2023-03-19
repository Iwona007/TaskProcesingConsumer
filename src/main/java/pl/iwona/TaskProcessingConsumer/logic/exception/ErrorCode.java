package pl.iwona.TaskProcessingConsumer.logic.exception;

/**
 * Business ErrorCode for application
 */
public enum ErrorCode {

    T004("T004", "TASK_NOT_FOUND", 404);

    private final String businessStatus;
    private final String businessMessage;
    private final Integer businessStatusCode;

    ErrorCode(String businessStatus, String businessMessage, Integer businessStatusCode) {
        this.businessStatus = businessStatus;
        this.businessMessage = businessMessage;
        this.businessStatusCode = businessStatusCode;
    }

    public String getBusinessStatus() {
        return this.businessStatus;
    }

    public String getBusinessMessage() {
        return this.businessMessage;
    }

    public Integer getBusinessStatusCode() {
        return this.businessStatusCode;
    }
}
