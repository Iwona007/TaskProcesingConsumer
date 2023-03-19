package pl.iwona.TaskProcessingConsumer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TaskResultStatusDto {

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
