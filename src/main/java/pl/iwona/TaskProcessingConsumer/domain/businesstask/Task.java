package pl.iwona.TaskProcessingConsumer.domain.businesstask;

import lombok.Builder;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;


/**
 * Business object Task
 */

public class Task {

    private Integer taskId;

    private String input;

    private String pattern;

    private TaskType taskType;

    private String result;

    private String status;
}
