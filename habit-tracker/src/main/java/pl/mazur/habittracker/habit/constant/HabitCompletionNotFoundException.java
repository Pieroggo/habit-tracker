package pl.mazur.habittracker.habit.constant;

public class HabitCompletionNotFoundException extends RuntimeException {
    public HabitCompletionNotFoundException(Long id) {
        super(String.format("Habit with id %d not found",id));
    }
}
