package pl.iwona.TaskProcessingConsumer.consumer;//package pl.iwona.KafkaTask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import pl.iwona.TaskProcessingConsumer.domain.entity.TaskEntity;

@Slf4j
@Service
@RequiredArgsConstructor
class TaskConsumerImpl implements TaskConsumer {

    private final ObjectMapper objectMapper;


    //todo zapytać, tutaj chyba powinnien być task biznesowy czy encja?
    @Override
    public TaskEntity processTaskEvent(ConsumerRecord<Integer, String> consumerRecord) {
        try {
            //recodr.value to by był moj taskId
           TaskEntity taskEntity = this.objectMapper.readValue(consumerRecord.value(), TaskEntity.class);
            log.info("taskEvent: {} ", taskEntity);
            return taskEntity;
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
