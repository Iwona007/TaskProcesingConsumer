package pl.iwona.TaskProcessingConsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.service.TaskServiceConsumer;
@Slf4j
@Component
public class TaskEventsConsumer {

    private TaskServiceConsumer taskServiceConsumer;
    @Autowired
    public TaskEventsConsumer(TaskServiceConsumer taskServiceConsumer) {
        this.taskServiceConsumer = taskServiceConsumer;
    }

    @KafkaListener(topics = {"task-events"}, groupId = "{tasks-events-listener-group}")
    public Task onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("ConsumerRecord: {} ", consumerRecord);
        return taskServiceConsumer.processTaskEvent(consumerRecord);
    }
}
