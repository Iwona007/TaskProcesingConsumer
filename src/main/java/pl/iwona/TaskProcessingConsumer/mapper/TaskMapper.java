package pl.iwona.TaskProcessingConsumer.mapper;

import org.mapstruct.Mapper;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskResultStatusDto;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task mapTaskResultStatusDtoToTaskEntity(TaskResultStatusDto taskResultStatusDto);

    TaskDto mapTaskEntityToTaskDTO(Task task);
}
