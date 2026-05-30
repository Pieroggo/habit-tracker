package pl.mazur.habittracker.goal.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.mazur.habittracker.goal.constant.GoalStatus;
import pl.mazur.habittracker.goal.constant.GoalUnit;
import pl.mazur.habittracker.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private BigDecimal targetValue;

    private BigDecimal currentValue;

    private GoalUnit unit;

    private GoalStatus status;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
