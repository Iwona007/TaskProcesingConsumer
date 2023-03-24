package pl.iwona.TaskProcessingConsumer.domain.mapper;

import org.mapstruct.Mapper;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.domain.entity.TaskEntity;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskResulDto;

/**
 * Mapper for data transfer object and task entity
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskEntity mapTaskResultStatusDtoToTaskEntity(TaskResulDto taskResulDto);

    TaskDto mapTaskEntityToTaskDto(TaskDto taskWithStatusAndResult);


}
