package pl.iwona.TaskProcessingConsumer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iwona.TaskProcessingConsumer.domain.Task;

@Repository
public interface TaskConsumerRepository extends JpaRepository<Task, Integer> {
}