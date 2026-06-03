package pl.mazur.habittracker.habit.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mazur.habittracker.habit.constant.HabitFrequency;
import pl.mazur.habittracker.user.domain.User;

import java.time.LocalDate;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private HabitFrequency frequency;

    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
