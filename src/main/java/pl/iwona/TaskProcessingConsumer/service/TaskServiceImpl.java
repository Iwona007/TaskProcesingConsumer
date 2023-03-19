package pl.iwona.TaskProcessingConsumer.service;//package pl.iwona.KafkaTask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;
import pl.iwona.TaskProcessingConsumer.repository.TaskConsumerRepository;

import java.util.UUID;


@Service
@Slf4j
public class TaskServiceImpl {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TaskConsumerRepository taskConsumerRepository;

    public void processTaskEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        try {
        Task task = this.objectMapper.readValue(consumerRecord.value(), Task.class);
        log.info("taskEvent: {} ", task);
            if(task.getTaskId() != null) {
                task.setTaskType(TaskType.IN_PROGRESS);
                taskConsumerRepository.save(task);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void readTaskById(Integer taskId) {

    }
}
