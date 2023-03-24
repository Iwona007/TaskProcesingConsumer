package pl.iwona.TaskProcessingConsumer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    private String input;

    private String pattern;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private String result;

    private String status;
}
