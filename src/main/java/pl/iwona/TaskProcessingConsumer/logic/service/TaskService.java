package pl.iwona.TaskProcessingConsumer.logic.service;

import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;

/**
 * TaskService service for business logic
 */
public interface TaskService {

    TaskDto getTaskWithStatusAndResult(Integer taskId);
}
