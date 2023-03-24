package pl.iwona.TaskProcessingConsumer.logic.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.domain.entity.TaskEntity;
import pl.iwona.TaskProcessingConsumer.domain.mapper.TaskMapper;
import pl.iwona.TaskProcessingConsumer.consumer.TaskConsumer;
import pl.iwona.TaskProcessingConsumer.logic.service.TaskService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
@Service
@RequiredArgsConstructor
public class TaskConsumerServiceFacade {

    private final TaskConsumer taskConsumer;

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    public TaskDto getTaskWithStatusAndResult(ConsumerRecord<Integer, String> consumerRecord) {

        final TaskEntity taskEntity = taskConsumer
                .processTaskEvent(consumerRecord);
      return taskService.getTaskWithStatusAndResult(taskEntity.getTaskId());
    }

}
