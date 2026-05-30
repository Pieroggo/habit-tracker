package pl.mazur.habittracker.habit.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mazur.habittracker.goal.domain.GoalProgress;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit,Long> {
    List<Habit> findAllByUserId(Long userId);
}