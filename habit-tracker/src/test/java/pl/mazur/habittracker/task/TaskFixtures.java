package pl.mazur.habittracker.task;

import pl.mazur.habittracker.task.domain.Task;
import pl.mazur.habittracker.user.UserFixtures;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskFixtures {

    public static Task.TaskBuilder baseTask() {
        return Task.builder()
                .id(1L)
                .title("Finish project")
                .description("Complete Spring Boot app")
                .completed(false)
                .dueDate(LocalDate.now().plusDays(7))
                .completedAt(null)
                .user(UserFixtures.withCompleteData());
    }

    public static Task withCompleteData() {
        return baseTask().build();
    }

    public static Task withTaskCompleted() {
        return baseTask()
                .completed(true)
                .completedAt(LocalDateTime.now())
                .build();
    }

    public static Task withTaskId(Long id) {
        return baseTask()
                .id(id)
                .build();
    }
}
