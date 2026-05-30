package pl.mazur.habittracker.habit.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.habit.constant.HabitNotFoundException;
import pl.mazur.habittracker.habit.constant.HabitCompletionNotFoundException;
import pl.mazur.habittracker.habit.domain.*;
import pl.mazur.habittracker.habit.internal.dto.HabitDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitCompletionDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitCompletionRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitCompletionService {
    private final HabitCompletionRepository habitCompletionRepository;
    private final HabitRepository habitRepository;
    private final HabitCompletionMapper habitCompletionMapper;

    public List<HabitCompletionDTO> findAllByHabitId(Long habitId){
        return habitCompletionRepository.findAllByHabitId(habitId).stream()
                .map(habitCompletionMapper::toHabitCompletionDTO)
                .toList();
    }
    public HabitCompletionDTO findById(Long habitId){
        return habitCompletionRepository.findById(habitId)
                .map(habitCompletionMapper::toHabitCompletionDTO)
                .orElseThrow(() -> new HabitCompletionNotFoundException(habitId));
    }
    public HabitCompletionDTO create(Long habitId, HabitCompletionRequest request){
        Habit habit=habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitNotFoundException(habitId));
        return habitCompletionMapper.toHabitCompletionDTO(
                habitCompletionRepository.save(habitCompletionMapper.toHabitCompletion(habit,request))
        );
    }
    public HabitCompletionDTO update(Long habitCompletionId, HabitCompletionRequest request){
        Habit habit=habitRepository.findById(request.getHabitId())
                .orElseThrow(() -> new HabitNotFoundException(request.getHabitId()));
        return habitCompletionRepository.findById(habitCompletionId)
                .map(habitCompletion -> habitCompletionMapper.toHabitCompletion(habitCompletion,request,habit))
                .map(habitCompletionRepository::save)
                .map(habitCompletionMapper::toHabitCompletionDTO)
                .orElseThrow(() -> new HabitCompletionNotFoundException(habitCompletionId));
    }
    public void delete(Long habitCompletionId){
        HabitCompletion habitCompletion=habitCompletionRepository.findById(habitCompletionId)
                .orElseThrow(() -> new HabitNotFoundException(habitCompletionId));
        habitCompletionRepository.delete(habitCompletion);
    }

    public void deleteAll(Long habitId){
        List<HabitCompletion> progressRecords = habitCompletionRepository.findAllByHabitId(habitId);
        habitCompletionRepository.deleteAll(progressRecords);
    }

}
