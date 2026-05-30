package pl.mazur.habittracker.habit.domain;

import org.springframework.stereotype.Component;
import pl.mazur.habittracker.habit.internal.dto.HabitCompletionDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitCompletionRequest;

@Component
public class HabitCompletionMapper {
    public HabitCompletionDTO toHabitCompletionDTO(HabitCompletion habitCompletion){
        return HabitCompletionDTO.builder()
                .id(habitCompletion.getId())
                .completedAt(habitCompletion.getCompletedAt())
                .note(habitCompletion.getNote())
                .build();
    }

    public HabitCompletion toHabitCompletion(Habit habit, HabitCompletionRequest habitCompletionRequest){
        return HabitCompletion.builder()
                .note(habitCompletionRequest.getNote())
                .completedAt(habitCompletionRequest.getCompletedAt())
                .habit(habit)
                .build();
    }

    public HabitCompletion toHabitCompletion(HabitCompletion habitCompletion, HabitCompletionRequest habitCompletionRequest, Habit habit){
        return habitCompletion.toBuilder()
                .note(habitCompletionRequest.getNote())
                .completedAt(habitCompletionRequest.getCompletedAt())
                .habit(habit)
                .build();
    }
}
