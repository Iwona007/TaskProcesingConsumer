package pl.iwona.TaskProcessingConsumer.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import pl.iwona.TaskProcessingConsumer.domain.entity.TaskEntity;

/**
 * TaskConsumer service for Kafka consumer
 */
public interface TaskConsumer {

    TaskEntity processTaskEvent(ConsumerRecord<Integer, String> consumerRecord);
}
