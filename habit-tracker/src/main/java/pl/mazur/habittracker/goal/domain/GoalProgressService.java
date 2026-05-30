package pl.mazur.habittracker.goal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.goal.constant.GoalNotFoundException;
import pl.mazur.habittracker.goal.constant.GoalProgressNotFoundException;
import pl.mazur.habittracker.goal.internal.dto.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalProgressService {
    private final GoalProgressRepository goalProgressRepository;
    private final GoalRepository goalRepository;
    private final GoalProgressMapper goalProgressMapper;

    public List<GoalProgressDTO> findAllByGoalId(Long goalId){
        return goalProgressRepository.findAllByGoalId(goalId).stream()
                .map(goalProgressMapper::toGoalProgressDTO)
                .toList();
    }
    public GoalProgressDTO findById(Long goalId){
        return goalProgressRepository.findById(goalId)
                .map(goalProgressMapper::toGoalProgressDTO)
                .orElseThrow(() -> new GoalProgressNotFoundException(goalId));
    }
    public GoalProgressDTO create(Long goalId, GoalProgressRequest request){
        Goal goal=goalRepository.findById(goalId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));
        return goalProgressMapper.toGoalProgressDTO(
                goalProgressRepository.save(goalProgressMapper.toGoalProgress(goal,request))
        );
    }
    public GoalProgressDTO update(Long goalProgressId, GoalProgressRequest request){
        Goal goal=goalRepository.findById(request.getGoalId())
                .orElseThrow(() -> new GoalNotFoundException(request.getGoalId()));
        return goalProgressRepository.findById(goalProgressId)
                .map(goalProgress -> goalProgressMapper.toGoalProgress(goalProgress,request,goal))
                .map(goalProgressRepository::save)
                .map(goalProgressMapper::toGoalProgressDTO)
                .orElseThrow(() -> new GoalProgressNotFoundException(goalProgressId));
    }
    public void delete(Long goalProgressId){
        GoalProgress goalProgress=goalProgressRepository.findById(goalProgressId)
                .orElseThrow(() -> new GoalNotFoundException(goalProgressId));
        goalProgressRepository.delete(goalProgress);
    }

    public void deleteAll(Long goalId){
        List<GoalProgress> progressRecords = goalProgressRepository.findAllByGoalId(goalId);
        goalProgressRepository.deleteAll(progressRecords);
    }

}
