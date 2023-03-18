package pl.iwona.TaskProcessingConsumer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
//@Table
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    private String input;

    private String pattern;

    private String result;

    private String status;
    @OneToOne
    @JoinColumn(name = "taskEventId")
    private TaskEvent taskEvent;

    public Task(String input, String pattern, String result, String status) {
        this.input = input;
        this.pattern = pattern;
        this.result = result;
        this.status = status;
    }
}
