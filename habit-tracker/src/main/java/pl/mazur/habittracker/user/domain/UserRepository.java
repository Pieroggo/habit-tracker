package pl.mazur.habittracker.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mazur.habittracker.habit.domain.Habit;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
