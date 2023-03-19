package pl.iwona.TaskProcessingConsumer.logic.service;//package pl.iwona.KafkaTask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iwona.TaskProcessingConsumer.domain.Task;
import pl.iwona.TaskProcessingConsumer.domain.TaskProgress;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskResulDto;
import pl.iwona.TaskProcessingConsumer.logic.exception.AppRuntimeException;
import pl.iwona.TaskProcessingConsumer.domain.mapper.TaskMapper;
import pl.iwona.TaskProcessingConsumer.logic.repository.TaskConsumerRepository;

import java.util.Optional;

@Slf4j
@Service
class TaskServiceConsumerImpl implements TaskServiceConsumer {

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
            Task foundedTask = findTaskById(task.getTaskId());
            try{
                Task taskInProgress = updateTaskToInProgress(foundedTask);
                log.info("taskInProgress: {}", taskInProgress);
                Thread.sleep(15000);
                Task taskBestMatch = findBestMatch(taskInProgress);
                log.info("taskBestMatch with done status: {}", taskBestMatch);
                return updateTaskToDone(taskBestMatch);

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public TaskDto getTaskWithStatusAndResult(Integer taskId) {
        var task = findTaskById(taskId);
        return TaskDto.builder()
                .status(task.getStatus())
                .result(task.getResult())
                .build();
    }

    private Task findTaskById(Integer taskId) {
        final Optional<Task> findTaskById = taskConsumerRepository.findById(taskId);
        if (findTaskById.isPresent()) {
            return findTaskById.get();
        } else {
            throw new AppRuntimeException("task not exist");
        }
    }

    private Task updateTaskToInProgress(Task foundedTask) {
        foundedTask.setTaskId(foundedTask.getTaskId());
        foundedTask.setPattern(foundedTask.getPattern());
        foundedTask.setInput(foundedTask.getInput());
        foundedTask.setTaskType(TaskType.IN_PROGRESS);
        foundedTask.setStatus(TaskProgress.TEN.getPercentage());
        log.info("task in progress status: {} ", foundedTask.getStatus());
        return taskConsumerRepository.save(foundedTask);
    }

    private Task findBestMatch(Task task) {
        String pattern = task.getPattern();
        String input = task.getInput();
        int patternLength = pattern.length();
        int position;
        int typos = patternLength;

        Task saveTaskWithBestMatch = null;
        try {
        for (int i = 0; i <= input.length() - patternLength; i++) {
            String currentSubstring = input.substring(i, patternLength + i);
            int currentTypos = countTypos(pattern, currentSubstring);
            log.info("in the loop best match");
            if (currentTypos < typos) {
                typos = currentTypos;
                position = i;
                saveTaskWithBestMatch = saveTaskWithBestMatch(task, position, typos);
            }
        }
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("get out loop");
        return saveTaskWithBestMatch;
    }

    private Task saveTaskWithBestMatch(Task task, int position, int typos) {
        Task saveTaskWithBestMatch;
        try {
        TaskResulDto buildtaskResulDto = TaskResulDto
                .builder()
                .taskId(task.getTaskId())
                .input(task.getInput())
                .pattern(task.getPattern())
                .position(position)
                .typos(typos)
                .result(position + ", " + typos)
                .status(TaskProgress.FIFTY.getPercentage())
                .taskType(task.getTaskType())
                .build();
        saveTaskWithBestMatch = taskConsumerRepository.save(taskMapper
                .mapTaskResultStatusDtoToTaskEntity(buildtaskResulDto));
            log.info("task with the best match: {} ", saveTaskWithBestMatch);
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return saveTaskWithBestMatch;
    }

    private int countTypos(String pattern, String input) {
        var counter = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != input.charAt(i)) {
                counter++;
            }
        }
        return counter;
    }

    private Task updateTaskToDone(Task task) throws InterruptedException {
        task.setTaskId(task.getTaskId());
        task.setInput(task.getInput());
        task.setPattern(task.getPattern());
        task.setResult(task.getResult());
        task.setStatus(TaskProgress.ONE_HUNDRED.getPercentage());
        task.setTaskType(TaskType.DONE);
        log.info("task with status: {}", task.getStatus());
        Thread.sleep(15000);
        return taskConsumerRepository.save(task);
    }
}
