package pl.mazur.habittracker.habit.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HabitStatsDTO {
    private double completionRate;
    private long totalCompletions;
    private String favouriteHabitName;
}
