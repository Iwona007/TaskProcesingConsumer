package pl.iwona.TaskProcessingConsumer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.service.TaskServiceImpl;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskEventsConsumer {

    @Autowired
    private TaskServiceImpl taskService;

    @KafkaListener(topics = {"task-events"}, groupId = "{tasks-events-listener-group}")
    public Task onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("ConsumerRecord: {} ", consumerRecord);
        return taskService.processTaskEvent(consumerRecord);
    }


}
