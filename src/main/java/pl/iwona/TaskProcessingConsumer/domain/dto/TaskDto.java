package pl.iwona.TaskProcessingConsumer.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object method getTaskWithStatusAndResult
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TaskDto {

    private String result;

    private String status;

    @Override
    public String toString() {
        return "TaskDTO{" +
                "result='" + result + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
