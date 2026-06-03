package pl.mazur.habittracker.habit.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.habit.constant.HabitNotFoundException;
import pl.mazur.habittracker.habit.constant.HabitCompletionNotFoundException;
import pl.mazur.habittracker.habit.domain.*;
import pl.mazur.habittracker.habit.internal.dto.HabitDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitCompletionDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitCompletionRequest;
import pl.mazur.habittracker.habit.internal.dto.HabitStatsDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        return habitCompletionRepository.findById(habitCompletionId)
                .map(habitCompletion -> habitCompletionMapper.toHabitCompletion(habitCompletion,request))
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

    public HabitStatsDTO getUserStats(Long userId) {
        List<Habit> habits = habitRepository.findAllByUserId(userId);
        List<HabitCompletion> completions = new ArrayList<>();

        for(Habit habit : habits){
            completions.addAll(habitCompletionRepository.findAllByHabitId(habit.getId()));
        }

        if (completions.isEmpty()) {
            return new HabitStatsDTO(0.0, 0L, null);
        }

        long totalCompletions = completions.size();

        long activeDays = completions.stream()
                .map(c -> c.getCompletedAt().toLocalDate())
                .distinct()
                .count();

        long firstDay = completions.stream()
                .map(c -> c.getCompletedAt().toLocalDate())
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now())
                .until(LocalDate.now())
                .getDays() + 1;

        double completionRate = firstDay == 0
                ? 0.0
                : (activeDays * 100.0) / firstDay;

        String favoriteHabit = completions.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getHabit().getName(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return new HabitStatsDTO(
                completionRate,
                totalCompletions,
                favoriteHabit
        );
    }

    public int getCurrentStreak(Long habitId, int maxBreaks) {

        List<HabitCompletion> completions =
                habitCompletionRepository.findByHabitIdOrderByCompletedAtDesc(habitId);

        if (completions.isEmpty()) {
            return 0;
        }

        Set<LocalDate> completionDates = completions.stream()
                .map(c -> c.getCompletedAt().toLocalDate())
                .collect(Collectors.toSet());

        LocalDate cursor = LocalDate.now();

        int streak = 0;
        int breaksUsed = 0;

        while (breaksUsed <= maxBreaks) {

            if (completionDates.contains(cursor)) {
                streak++;
            } else {
                breaksUsed++;
            }

            cursor = cursor.minusDays(1);
        }

        return streak;
    }


}
