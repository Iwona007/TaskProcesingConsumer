package pl.iwona.TaskProcessingConsumer.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.logic.service.TaskServiceConsumer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/app")
public class TaskEventController {

    private final TaskServiceConsumer taskServiceConsumer;

    @Autowired
    public TaskEventController(TaskServiceConsumer taskServiceConsumer) {
        this.taskServiceConsumer = taskServiceConsumer;
    }

    @GetMapping("/result/{taskId}")
    public ResponseEntity<TaskDto> getTaskWithStatusAndResult(@Min(1) @NotNull @PathVariable Integer taskId) {
        final TaskDto task = taskServiceConsumer.getTaskWithStatusAndResult(taskId);
        log.info("result and status: {}", task);
        return new ResponseEntity<>(task, HttpStatus.FOUND);
    }
}
