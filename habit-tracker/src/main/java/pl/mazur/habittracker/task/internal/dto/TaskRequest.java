package pl.mazur.habittracker.task.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;

    private String description;

    private boolean completed;

    private LocalDate dueDate;

    private LocalDateTime completedAt;

    private Long userId;
}
