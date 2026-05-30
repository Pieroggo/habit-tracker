package pl.mazur.habittracker.habit.constant;

public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException(Long id) {
        super(
               String.format("Habit with id %d not found",id)
        );
    }
}
