package pl.mazur.habittracker.task;

import pl.mazur.habittracker.task.internal.dto.TaskDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskDTOFixtures {
    public static TaskDTO.TaskDTOBuilder baseTaskDTO() {
        return TaskDTO.builder()
                .id(1L)
                .title("Finish project")
                .description("Complete Spring Boot app")
                .completed(false)
                .dueDate(LocalDate.now().plusDays(7))
                .completedAt(null);
    }

    public static TaskDTO withCompleteData() {
        return baseTaskDTO().build();
    }

    public static TaskDTO withTaskCompleted() {
        return baseTaskDTO()
                .completed(true)
                .completedAt(LocalDateTime.now())
                .build();
    }

    public static TaskDTO withTaskId(Long id) {
        return baseTaskDTO()
                .id(id)
                .build();
    }
}
