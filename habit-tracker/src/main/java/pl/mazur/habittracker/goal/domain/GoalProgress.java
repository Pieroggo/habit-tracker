package pl.mazur.habittracker.goal.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoalProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valueAdded;

    private BigDecimal valueAfterUpdate;

    private LocalDateTime updatedAt;

    private String note;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;
}
