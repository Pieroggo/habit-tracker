package pl.mazur.habittracker.goal.domain;

import org.springframework.stereotype.Component;
import pl.mazur.habittracker.goal.internal.dto.GoalDTO;
import pl.mazur.habittracker.goal.internal.dto.GoalProgressDTO;
import pl.mazur.habittracker.goal.internal.dto.GoalRequest;
import pl.mazur.habittracker.goal.internal.dto.GoalWithProgressDTO;
import pl.mazur.habittracker.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoalMapper {
    public GoalDTO toGoalDTO(Goal goal){
        return GoalDTO.builder()
                .id(goal.getId())
                .title(goal.getTitle())
                .description(goal.getDescription())
                .currentValue(goal.getCurrentValue())
                .targetValue(goal.getTargetValue())
                .status(goal.getStatus())
                .unit(goal.getUnit())
                .deadline(goal.getDeadline())
                .build();
    }

    public GoalWithProgressDTO toGoalWithProgressDTO(GoalDTO goalDTO, List<GoalProgressDTO> progressRecords){
        return GoalWithProgressDTO.builder()
                .id(goalDTO.getId())
                .title(goalDTO.getTitle())
                .description(goalDTO.getDescription())
                .currentValue(goalDTO.getCurrentValue())
                .targetValue(goalDTO.getTargetValue())
                .status(goalDTO.getStatus())
                .unit(goalDTO.getUnit())
                .deadline(goalDTO.getDeadline())
                .progressRecords(progressRecords)
                .build();
    }

    public Goal toGoal(GoalRequest createGoalRequest, User user){
        return Goal.builder()
                .title(createGoalRequest.getTitle())
                .description(createGoalRequest.getDescription())
                .currentValue(createGoalRequest.getCurrentValue())
                .targetValue(createGoalRequest.getTargetValue())
                .status(createGoalRequest.getStatus())
                .unit(createGoalRequest.getUnit())
                .deadline(createGoalRequest.getDeadline())
                .user(user)
                .build();
    }

    public Goal toGoal(Goal goal,GoalRequest goalRequest,User user){
        return goal.toBuilder()
                .title(goalRequest.getTitle())
                .description(goalRequest.getDescription())
                .currentValue(goalRequest.getCurrentValue())
                .targetValue(goalRequest.getTargetValue())
                .status(goalRequest.getStatus())
                .unit(goalRequest.getUnit())
                .deadline(goalRequest.getDeadline())
                .user(user)
                .build();
    }
}
