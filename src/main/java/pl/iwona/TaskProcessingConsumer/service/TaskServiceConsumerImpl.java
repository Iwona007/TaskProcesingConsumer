package pl.iwona.TaskProcessingConsumer.service;//package pl.iwona.KafkaTask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskResultStatusDto;
import pl.iwona.TaskProcessingConsumer.exception.TaskNotExist;
import pl.iwona.TaskProcessingConsumer.mapper.TaskMapper;
import pl.iwona.TaskProcessingConsumer.repository.TaskConsumerRepository;

import java.util.Optional;

@Slf4j
@Service
public class TaskServiceConsumerImpl implements TaskServiceConsumer {

    private final TaskMapper taskMapper;

    private final ObjectMapper objectMapper;

    private final TaskConsumerRepository taskConsumerRepository;

    @Autowired
    public TaskServiceConsumerImpl(TaskMapper taskMapper, ObjectMapper objectMapper,
                                   TaskConsumerRepository taskConsumerRepository) {
        this.taskMapper = taskMapper;
        this.objectMapper = objectMapper;
        this.taskConsumerRepository = taskConsumerRepository;
    }

    @Override
    public Task processTaskEvent(ConsumerRecord<Integer, String> consumerRecord) {
        try {
            Task task = this.objectMapper.readValue(consumerRecord.value(), Task.class);
            log.info("taskEvent: {} ", task);
            //0 0. wyszukja w bazie danych czy taski z podanymi imputamia i paternami ze statusem "done" jeśli znajdziesz zwróć identyfikator teo zadania.
            //1 Odczytaj z bazy taska po Id
            Task foundedTask = findTaskById(task.getTaskId());
//       2 Zapisz taska ze statusem in Progres
            try {
                Task taskInProgress = updateTaskToInProgress(foundedTask);
                Thread.sleep(2000);
                // 3. Wyszukiwanie wzorca (kolumna progess, przejde 1 etap to zapisuje 25%, i przejde kolejny etap to zapisuje 50% i
                // daje tread sleep)
                Task taskBestMatch = findBestMatch(taskInProgress);
                // 4. Zapisz taska ze statusem DONE + zapisz wyniki przetwarzenia też z taskiem
                return updateTaskToDone(taskBestMatch);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TaskDto> getTaskWithStatusAndResult(Integer taskId) {

        return taskConsumerRepository
                .findById(taskId)
                .map(task -> {
                    return TaskDto.builder()
                            .status(task.getStatus())
                            .result(task.getResult())
                            .build();
                });
    }

    private Task findTaskById(Integer taskId) {
        final Optional<Task> findTaskById = taskConsumerRepository.findById(taskId);
        log.info("in methode");
        if (findTaskById.isPresent()) {
            log.info("in if");
            return findTaskById.get();
        } else {
            throw new TaskNotExist("task not exist");
        }
    }

    private Task updateTaskToInProgress(Task foundedTask) {
        foundedTask.setTaskId(foundedTask.getTaskId());
        foundedTask.setPattern(foundedTask.getPattern());
        foundedTask.setInput(foundedTask.getInput());
        foundedTask.setTaskType(TaskType.IN_PROGRESS);
        foundedTask.setStatus("10%");
        return taskConsumerRepository.save(foundedTask);
    }

    private Task findBestMatch(Task task) {
        String pattern = task.getPattern();
        String input = task.getInput();
        int patternLength = pattern.length();
        int position;
        int typos = patternLength;

        Task save = null;

        for (int i = 0; i <= input.length() - patternLength; i++) {
            String currentSubstring = input.substring(i, patternLength + i);
            int currentTypos = countTypos(pattern, currentSubstring);
            log.info("in the loop");
            if (currentTypos < typos) {
                typos = currentTypos;
                position = i;
                TaskResultStatusDto buildtaskResultStatusDto = TaskResultStatusDto
                        .builder()
                        .taskId(task.getTaskId())
                        .input(task.getInput())
                        .pattern(task.getPattern())
                        .position(position)
                        .typos(typos)
                        .result(position + ", " + typos)
                        .status("50%")
                        .taskType(task.getTaskType())
                        .build();
                save = taskConsumerRepository.save(taskMapper
                        .mapTaskResultStatusDtoToTaskEntity(buildtaskResultStatusDto));
            }
        }
        log.info("get out loop");
        return save;
    }

    private int countTypos(String pattern, String input) {
        int counter = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != input.charAt(i)) {
                counter++;
            }
        }
        return counter;
    }

    private Task updateTaskToDone(Task task) {
        task.setTaskId(task.getTaskId());
        task.setInput(task.getInput());
        task.setPattern(task.getPattern());
        task.setResult(task.getResult());
        task.setStatus("100%");
        task.setTaskType(TaskType.DONE);
        return taskConsumerRepository.save(task);
    }
}
