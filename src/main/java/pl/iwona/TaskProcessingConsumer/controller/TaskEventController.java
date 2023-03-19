package pl.iwona.TaskProcessingConsumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.service.TaskServiceConsumer;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/app")
public class TaskEventController {

    private TaskServiceConsumer taskServiceConsumer;

    @Autowired
    public TaskEventController(TaskServiceConsumer taskServiceConsumer) {
        this.taskServiceConsumer = taskServiceConsumer;
    }

    @GetMapping("/result/{taskId}")
    public ResponseEntity<Optional<TaskDto>> getTaskWithStatusAndResult(@PathVariable Integer taskId) {
        final Optional<TaskDto> task = taskServiceConsumer.getTaskWithStatusAndResult(taskId);
        log.info("result and status: {}", task);
        return new ResponseEntity<>(task, HttpStatus.FOUND);
    }
}
