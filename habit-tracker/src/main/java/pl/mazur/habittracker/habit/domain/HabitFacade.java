package pl.mazur.habittracker.habit.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mazur.habittracker.habit.internal.dto.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HabitFacade {

    private final HabitService habitService;
    private final HabitCompletionService habitCompletionService;
    private final HabitMapper habitMapper;


    public List<HabitDTO> findAllHabitsByUserId(Long userId) {
        return habitService.findAllByUserId(userId);
    }

    public HabitWithCompletionsDTO findHabitById(Long habitId) {
        HabitDTO habitDTO=habitService.findById(habitId);
        List<HabitCompletionDTO> completionRecords = habitCompletionService.findAllByHabitId(habitDTO.getId());
        return habitMapper.toHabitWithCompletionsDTO(habitDTO,completionRecords);
    }

    public HabitDTO createHabit(HabitRequest request) {
        return habitService.create(request);
    }

    public HabitDTO updateHabit(Long habitId, HabitRequest request) {
        return habitService.update(habitId, request);
    }

    public void deleteHabit(Long habitId) {
        habitService.delete(habitId);
        habitCompletionService.deleteAll(habitId);
    }

    public HabitCompletionDTO createCompletion(Long habitId, HabitCompletionRequest request) {
        return habitCompletionService.create(habitId, request);
    }

    public HabitCompletionDTO updateCompletion(Long completionId, HabitCompletionRequest request) {
        return habitCompletionService.update(completionId, request);
    }

    public void deleteCompletion(Long completionId) {
        habitCompletionService.delete(completionId);
    }

    public HabitStatsDTO getHabitStats(Long userId){
        return habitCompletionService.getUserStats(userId);
    }
}
