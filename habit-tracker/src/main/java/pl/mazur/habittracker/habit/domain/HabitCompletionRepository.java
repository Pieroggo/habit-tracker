package pl.mazur.habittracker.habit.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion,Long> {
    List<HabitCompletion> findAllByHabitId(Long habitId);
}