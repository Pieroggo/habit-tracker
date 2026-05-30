package pl.mazur.habittracker.goal.constant;

public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException(Long id) {
        super(String.format("Goal with id %d was not found",id ));
    }
}
