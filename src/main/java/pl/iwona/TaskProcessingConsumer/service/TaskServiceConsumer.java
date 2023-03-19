package pl.iwona.TaskProcessingConsumer.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;

import java.util.Optional;

public interface TaskServiceConsumer {

    Task processTaskEvent(ConsumerRecord<Integer, String> consumerRecord);

    Optional<TaskDto> getTaskWithStatusAndResult(Integer taskId);
}
