package pl.iwona.TaskProcessingConsumer.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.logic.fasade.TaskConsumerServiceFacade;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class TaskEventController {

    private final TaskConsumerServiceFacade taskConsumerServiceFacade;

    @GetMapping("/result/{taskId}")
    public ResponseEntity<TaskDto> getTaskWithStatusAndResult(@Min(1) @NotNull @PathVariable
                                                                  ConsumerRecord<Integer, String> consumerRecord) {
        final TaskDto task = taskConsumerServiceFacade.getTaskWithStatusAndResult(consumerRecord);
        log.info("result and status: {}", task);
        return new ResponseEntity<>(task, HttpStatus.FOUND);
    }
}
