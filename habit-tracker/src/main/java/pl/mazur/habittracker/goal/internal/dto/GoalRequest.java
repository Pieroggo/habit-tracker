package pl.mazur.habittracker.goal.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mazur.habittracker.goal.constant.GoalStatus;
import pl.mazur.habittracker.goal.constant.GoalUnit;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoalRequest {
    private String title;
    private String description;
    private BigDecimal targetValue;
    private BigDecimal currentValue;
    private GoalUnit unit;
    private GoalStatus status;
    private LocalDate deadline;
    private Long userId;
}
