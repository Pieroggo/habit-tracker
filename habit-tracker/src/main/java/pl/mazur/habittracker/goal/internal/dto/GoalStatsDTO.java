package pl.mazur.habittracker.goal.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoalStatsDTO {
    private long totalGoals;
    private long completedGoals;
    private long activeGoals;
    private long overdueGoals;
    private BigDecimal completionRate;
    private BigDecimal averageProgress;
    private BigDecimal totalTargetValue;
    private BigDecimal totalAchievedValue;
}
