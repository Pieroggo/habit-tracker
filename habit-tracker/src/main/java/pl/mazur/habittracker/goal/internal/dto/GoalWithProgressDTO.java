package pl.mazur.habittracker.goal.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mazur.habittracker.goal.constant.GoalStatus;
import pl.mazur.habittracker.goal.constant.GoalUnit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoalWithProgressDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal targetValue;
    private BigDecimal currentValue;
    private GoalUnit unit;
    private GoalStatus status;
    private LocalDate deadline;
    private List<GoalProgressDTO> progressRecords;
}
