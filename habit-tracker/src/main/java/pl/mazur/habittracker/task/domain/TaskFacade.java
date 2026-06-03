package pl.mazur.habittracker.task.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mazur.habittracker.task.internal.dto.TaskDTO;
import pl.mazur.habittracker.task.internal.dto.TaskRequest;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskService taskService;

    public List<TaskDTO> findAllTasksByUserId(Long userId) {
        return taskService.findAllByUserId(userId);
    }

    public TaskDTO findTaskById(Long taskId) {
        return taskService.findById(taskId);
    }

    public TaskDTO createTask(TaskRequest request) {
        return taskService.create(request);
    }

    public TaskDTO updateTask(Long taskId, TaskRequest request) {
        return taskService.update(taskId, request);
    }

    public void deleteTask(Long taskId) {
        taskService.delete(taskId);
    }

    public Optional<TaskDTO> completeTask(Long taskId){return taskService.complete(taskId);}
}
