package pl.mazur.habittracker.task.domain;

import org.springframework.stereotype.Component;
import pl.mazur.habittracker.task.internal.dto.TaskDTO;
import pl.mazur.habittracker.task.internal.dto.TaskRequest;
import pl.mazur.habittracker.user.domain.User;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public TaskDTO toTaskDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .dueDate(task.getDueDate())
                .completedAt(task.getCompletedAt())
                .build();
    }

    public Task toTask(TaskRequest request, User user) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .dueDate(request.getDueDate())
                .completedAt(request.getCompletedAt())
                .user(user)
                .build();
    }

    public Task toTask(Task task, TaskRequest request,User user) {
        return task.toBuilder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .completed(request.isCompleted())
                .completedAt(request.getCompletedAt())
                .user(user)
                .build();

    }
}