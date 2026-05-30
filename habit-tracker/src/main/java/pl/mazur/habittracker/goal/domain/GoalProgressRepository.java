package pl.mazur.habittracker.goal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalProgressRepository extends JpaRepository<GoalProgress,Long> {
    List<GoalProgress> findAllByGoalId(Long goalId);
}