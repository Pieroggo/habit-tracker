package pl.mazur.habittracker.task.constant;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super(String.format("Habit with id %d not found",id));
    }
}
