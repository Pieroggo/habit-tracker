package pl.mazur.habittracker.task;

import pl.mazur.habittracker.task.internal.dto.TaskRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskRequestFixtures {
    public static TaskRequest.TaskRequestBuilder baseTaskRequest() {
        return TaskRequest.builder()
                .title("Finish project")
                .description("Complete Spring Boot app")
                .completed(false)
                .dueDate(LocalDate.now().plusDays(7))
                .completedAt(null)
                .userId(1L);
    }

    public static TaskRequest withCompleteData(){
        return baseTaskRequest().build();
    }

    public static TaskRequest withUserId(Long userId){
        return baseTaskRequest()
                .userId(userId)
                .build();
    }

    public static TaskRequest withUserIdAndCompleted(Long userId){
        return baseTaskRequest()
                .userId(userId)
                .completed(true)
                .completedAt(LocalDateTime.now())
                .build();
    }
}
