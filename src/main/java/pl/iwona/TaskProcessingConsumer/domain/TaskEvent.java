package pl.iwona.TaskProcessingConsumer.domain;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
//@Entity
//@Table(name = "task_event")
public class TaskEvent {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID taskEventId;
//    @Enumerated(EnumType.STRING)
    private TaskType taskType;
//    @OneToOne(mappedBy = "taskEvent", cascade = CascadeType.ALL)
//    @ToString.Exclude
//    @Valid
    private Task task;

    public TaskEvent(TaskType taskType, Task task) {
        this.taskType = taskType;
        this.task = task;
    }
}
