package pl.mazur.habittracker.habit.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HabitCompletionDTO {
    private Long id;
    private LocalDateTime completedAt;
    private String note;
}
