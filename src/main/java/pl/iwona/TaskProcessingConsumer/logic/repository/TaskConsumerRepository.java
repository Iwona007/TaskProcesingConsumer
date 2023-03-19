package pl.iwona.TaskProcessingConsumer.logic.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.iwona.TaskProcessingConsumer.domain.Task;

@Repository
public interface TaskConsumerRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT status, result FROM tasks WHERE pattern = :pattern AND input = :input", nativeQuery = true)
    Task findByPatternAndInput(@Param("pattern") String pattern, @Param("input") String input);
}