package pl.mazur.habittracker.goal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.goal.constant.GoalNotFoundException;
import pl.mazur.habittracker.goal.constant.GoalProgressNotFoundException;
import pl.mazur.habittracker.goal.constant.GoalStatus;
import pl.mazur.habittracker.goal.internal.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    public GoalStatsDTO getStats(Long userId) {

        List<Goal> goals = goalRepository.findAllByUserId(userId);

        long totalGoals = goals.size();

        long completedGoals = goals.stream()
                .filter(goal -> goal.getStatus() == GoalStatus.COMPLETED)
                .count();

        long activeGoals = goals.stream()
                .filter(goal -> goal.getStatus() == GoalStatus.ACTIVE)
                .count();

        long overdueGoals = goals.stream()
                .filter(goal ->
                        goal.getStatus() != GoalStatus.COMPLETED
                                && goal.getDeadline() != null
                                && goal.getDeadline().isBefore(LocalDate.now()))
                .count();

        BigDecimal completionRate = totalGoals == 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(completedGoals)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalGoals), 2, RoundingMode.HALF_UP);

        BigDecimal totalTargetValue = goals.stream()
                .map(Goal::getTargetValue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAchievedValue = goals.stream()
                .map(Goal::getCurrentValue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageProgress = goals.isEmpty()
                ? BigDecimal.ZERO
                : goals.stream()
                .map(this::calculateGoalProgress)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(goals.size()), 2, RoundingMode.HALF_UP);

        return GoalStatsDTO.builder()
                .totalGoals(totalGoals)
                .completedGoals(completedGoals)
                .activeGoals(activeGoals)
                .overdueGoals(overdueGoals)
                .completionRate(completionRate)
                .averageProgress(averageProgress)
                .totalTargetValue(totalTargetValue)
                .totalAchievedValue(totalAchievedValue)
                .build();
    }

    private BigDecimal calculateGoalProgress(Goal goal) {

        if (goal.getTargetValue() == null
                || BigDecimal.ZERO.compareTo(goal.getTargetValue()) == 0
                || goal.getCurrentValue() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal progress = goal.getCurrentValue()
                .multiply(BigDecimal.valueOf(100))
                .divide(goal.getTargetValue(), 2, RoundingMode.HALF_UP);

        return progress.min(BigDecimal.valueOf(100));
    }
}
