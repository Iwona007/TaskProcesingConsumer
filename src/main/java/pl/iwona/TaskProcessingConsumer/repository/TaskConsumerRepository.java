package pl.iwona.TaskProcessingConsumer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.iwona.TaskProcessingConsumer.domain.Task;

public interface TaskConsumerRepository extends JpaRepository<Task, Integer> {
}
