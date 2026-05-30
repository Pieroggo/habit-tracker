package pl.mazur.habittracker.goal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mazur.habittracker.goal.internal.dto.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoalFacade {
    private final GoalService goalService;
    private final GoalMapper goalMapper;
    private final GoalProgressService goalProgressService;

    public List<GoalDTO> findAllGoalsByUserId(Long userId){
        return goalService.findAllByUserId(userId);
    }

    public GoalWithProgressDTO findGoalById(Long goalId){
        GoalDTO goalDTO = goalService.findById(goalId);
        List<GoalProgressDTO> progressRecords=goalProgressService.findAllByGoalId(goalDTO.getId());
        return goalMapper.toGoalWithProgressDTO(goalDTO,progressRecords);
    }

    public GoalDTO createGoal(GoalRequest request){
        return goalService.create(request);
    }

    public GoalProgressDTO createGoalProgress(Long goalId,GoalProgressRequest request){
        return goalProgressService.create(goalId,request);
    }

    public GoalDTO updateGoal(Long goalId, GoalRequest request){
        return goalService.update(goalId,request);
    }

    public GoalProgressDTO updateGoalProgress(Long goalProgressId, GoalProgressRequest request){
        return goalProgressService.update(goalProgressId,request);
    }

    public void deleteGoal(Long goalId){
        goalService.delete(goalId);
        goalProgressService.deleteAll(goalId);
    }

    public void deleteGoalProgress(Long goalProgressId){
        goalProgressService.delete(goalProgressId);
    }
}
