package pl.iwona.TaskProcessingConsumer.logic.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;

/**
 * TaskService For consumer Kafka
 */
public interface TaskServiceConsumer {

    Task processTaskEvent(ConsumerRecord<Integer, String> consumerRecord);

    TaskDto getTaskWithStatusAndResult(Integer taskId);
}
