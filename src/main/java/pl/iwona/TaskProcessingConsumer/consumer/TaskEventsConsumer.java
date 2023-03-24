package pl.iwona.TaskProcessingConsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.iwona.TaskProcessingConsumer.domain.entity.TaskEntity;

@Slf4j
@Component
public class TaskEventsConsumer {

    private final TaskConsumer taskConsumer;
    @Autowired
    public TaskEventsConsumer(TaskConsumer taskConsumer) {
        this.taskConsumer = taskConsumer;
    }

    //kafka jako dto, algorytm logika
    @KafkaListener(topics = {"task-events"}, groupId = "{tasks-events-listener-group}")
    public TaskEntity onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("ConsumerRecord: {} ", consumerRecord);
        return taskConsumer.processTaskEvent(consumerRecord);
    }
}
