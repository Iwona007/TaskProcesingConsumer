package pl.iwona.TaskProcessingConsumer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;

/**
 * Data transfer object for Task Entity
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TaskResulDto {

    private int taskId;

    private String input;

    private String pattern;

    private int position;

    private int typos;

    private String result;

    private String status;

    private TaskType taskType;

    public String getResult() {
        return getPosition() + ", "+ getTypos();
    }
}
