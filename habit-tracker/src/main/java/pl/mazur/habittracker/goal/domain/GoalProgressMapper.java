package pl.mazur.habittracker.goal.domain;

import org.springframework.stereotype.Component;
import pl.mazur.habittracker.goal.internal.dto.*;

@Component
public class GoalProgressMapper {
    public GoalProgressDTO toGoalProgressDTO(GoalProgress goalProgress){
        return GoalProgressDTO.builder()
                .id(goalProgress.getId())
                .valueAdded(goalProgress.getValueAdded())
                .valueAfterUpdate(goalProgress.getValueAfterUpdate())
                .updatedAt(goalProgress.getUpdatedAt())
                .note(goalProgress.getNote())
                .build();
    }

    public GoalProgress toGoalProgress(Goal goal,GoalProgressRequest request){
        return GoalProgress.builder()
                .valueAdded(request.getValueAdded())
                .valueAfterUpdate(request.getValueAfterUpdate())
                .updatedAt(request.getUpdatedAt())
                .note(request.getNote())
                .goal(goal)
                .build();
    }

    public GoalProgress toGoalProgress(GoalProgress goalProgress,GoalProgressRequest request,Goal goal){
        return goalProgress.toBuilder()
                .valueAdded(request.getValueAdded())
                .valueAfterUpdate(request.getValueAfterUpdate())
                .updatedAt(request.getUpdatedAt())
                .note(request.getNote())
                .goal(goal)
                .build();
    }
}
