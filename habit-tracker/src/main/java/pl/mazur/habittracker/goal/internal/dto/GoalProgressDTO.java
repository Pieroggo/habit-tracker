package pl.mazur.habittracker.goal.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoalProgressDTO {
    private Long id;
    private BigDecimal valueAdded;
    private BigDecimal valueAfterUpdate;
    private LocalDateTime updatedAt;
    private String note;
    private Long goalId;
}
