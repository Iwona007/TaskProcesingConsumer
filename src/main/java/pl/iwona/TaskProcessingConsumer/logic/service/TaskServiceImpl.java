package pl.iwona.TaskProcessingConsumer.logic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.iwona.TaskProcessingConsumer.domain.TaskProgress;
import pl.iwona.TaskProcessingConsumer.domain.TaskType;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskDto;
import pl.iwona.TaskProcessingConsumer.domain.dto.TaskResulDto;
import pl.iwona.TaskProcessingConsumer.domain.entity.TaskEntity;
import pl.iwona.TaskProcessingConsumer.domain.mapper.TaskMapper;
import pl.iwona.TaskProcessingConsumer.logic.exception.AppRuntimeException;
import pl.iwona.TaskProcessingConsumer.logic.repository.TaskConsumerRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskConsumerRepository taskConsumerRepository;

    private final TaskMapper taskMapper;

    //todo czy dobry typ zwracany??
    public TaskEntity method(Integer taskId) {

        TaskEntity foundedTaskEntity = findTaskById(taskId);
        try {
            TaskEntity taskEntityInProgress = updateTaskToInProgress(foundedTaskEntity);
            log.info("taskInProgress: {}", taskEntityInProgress);
            Thread.sleep(15000);
            TaskEntity taskEntityBestMatch = findBestMatch(taskEntityInProgress); //task nie musi wiedzieć o Kafce, testy prztwarzania danych
            // do find best match aby napisać tety junit to rozdzelić jakąś warstwą, czy główne przetwarznie programu wiec cos o bazie danych, albo o Kafce
            //jeśli swrwis wie cos o dto to żle, pisać więcej testów to pomoże w architekturze,
            log.info("taskBestMatch with done status: {}", taskEntityBestMatch);
            return updateTaskToDone(taskEntityBestMatch);
        } catch (InterruptedException ex) {
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

    private TaskEntity findTaskById(Integer taskId) {
        final Optional<TaskEntity> findTaskById = taskConsumerRepository.findById(taskId);
        if (findTaskById.isPresent()) {
            return findTaskById.get();
        } else {
            throw new AppRuntimeException("task not exist");
        }
    }

    private TaskEntity updateTaskToInProgress(TaskEntity foundedTaskEntity) {
        foundedTaskEntity.setTaskId(foundedTaskEntity.getTaskId());
        foundedTaskEntity.setPattern(foundedTaskEntity.getPattern());
        foundedTaskEntity.setInput(foundedTaskEntity.getInput());
        foundedTaskEntity.setTaskType(TaskType.IN_PROGRESS);
        foundedTaskEntity.setStatus(TaskProgress.TEN.getPercentage());
        log.info("task in progress status: {} ", foundedTaskEntity.getStatus());
        return taskConsumerRepository.save(foundedTaskEntity);
    }

    private TaskEntity findBestMatch(TaskEntity taskEntity) {
        String pattern = taskEntity.getPattern();
        String input = taskEntity.getInput();
        int patternLength = pattern.length();
        int position;
        int typos = patternLength;

        TaskEntity saveTaskWithBestMatchEntity = null;
        try {
            for (int i = 0; i <= input.length() - patternLength; i++) {
                String currentSubstring = input.substring(i, patternLength + i);
                int currentTypos = countTypos(pattern, currentSubstring);
                log.info("in the loop best match");
                if (currentTypos < typos) {
                    typos = currentTypos;
                    position = i;
                    saveTaskWithBestMatchEntity = saveTaskWithBestMatch(taskEntity, position, typos);
                }
            }
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("get out loop");
        return saveTaskWithBestMatchEntity;
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

    private TaskEntity saveTaskWithBestMatch(TaskEntity task, int position, int typos) {
        TaskEntity saveTaskWithBestMatch;
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

    private TaskEntity updateTaskToDone(TaskEntity taskEntity) throws InterruptedException {
        taskEntity.setTaskId(taskEntity.getTaskId());
        taskEntity.setInput(taskEntity.getInput());
        taskEntity.setPattern(taskEntity.getPattern());
        taskEntity.setResult(taskEntity.getResult());
        taskEntity.setStatus(TaskProgress.ONE_HUNDRED.getPercentage());
        taskEntity.setTaskType(TaskType.DONE);
        log.info("task with status: {}", taskEntity.getStatus());
        Thread.sleep(15000);
        return taskConsumerRepository.save(taskEntity);
    }
}
