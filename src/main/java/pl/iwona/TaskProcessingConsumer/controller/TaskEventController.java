package pl.iwona.TaskProcessingConsumer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.service.TaskServiceImpl;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class TaskEventController {

    @Autowired
    private TaskServiceImpl taskService;

    @GetMapping("/result/{taskId}")
    public ResponseEntity<Optional<TaskDto>> getTaskWithStatusAndResult(@PathVariable Integer taskId) {
        final Optional<TaskDto> task= taskService.getTaskWithStatusAndResult(taskId);
            log.info("rasult and status: {}", task);
        return new ResponseEntity<>(task, HttpStatus.FOUND);
    }
}
