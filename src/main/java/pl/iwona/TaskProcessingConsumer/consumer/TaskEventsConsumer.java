package pl.iwona.TaskProcessingConsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.iwona.TaskProcessingConsumer.service.TaskServiceImpl;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskEventsConsumer {

    @Autowired
    TaskServiceImpl taskService;

    @KafkaListener(topics = {"task-events"}, groupId = "{tasks-events-listener-group}")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord: {} ", consumerRecord);
        taskService.processTaskEvent(consumerRecord);
    }



}
