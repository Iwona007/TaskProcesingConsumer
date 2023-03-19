package pl.iwona.TaskProcessingConsumer.domain.mapper;

import org.mapstruct.Mapper;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskResulDto;

/**
 * Mapper for data transfer object and task entity
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task mapTaskResultStatusDtoToTaskEntity(TaskResulDto taskResulDto);
}
