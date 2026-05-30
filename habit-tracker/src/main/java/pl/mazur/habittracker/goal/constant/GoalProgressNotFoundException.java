package pl.mazur.habittracker.goal.constant;

public class GoalProgressNotFoundException extends RuntimeException {
    public GoalProgressNotFoundException(Long id) {
        super(String.format("GoalProgress with id %d was not found",id ));
    }
}
