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

    private Integer position;

    private Integer typos;

    private String status;

    private String result;

    @Override
    public String toString() {
        return "TaskDto{" +
                "position=" + position +
                ", typos=" + typos +
                ", status='" + status + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
