package pl.mazur.habittracker.habit.domain;

import org.springframework.stereotype.Component;
import pl.mazur.habittracker.habit.internal.dto.HabitCompletionDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitRequest;
import pl.mazur.habittracker.habit.internal.dto.HabitWithCompletionsDTO;
import pl.mazur.habittracker.user.domain.User;

import java.util.List;

@Component
public class HabitMapper {
    public HabitDTO toHabitDTO(Habit habit){
        return HabitDTO.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .frequency(habit.getFrequency())
                .startDate(habit.getStartDate())
                .archived(habit.isArchived())
                .build();
    }
    public HabitWithCompletionsDTO toHabitWithCompletionsDTO(HabitDTO habitDTO, List<HabitCompletionDTO> habitCompletionRecords){
        return HabitWithCompletionsDTO.builder()
                .id(habitDTO.getId())
                .name(habitDTO.getName())
                .description(habitDTO.getDescription())
                .frequency(habitDTO.getFrequency())
                .startDate(habitDTO.getStartDate())
                .archived(habitDTO.isArchived())
                .completionRecords(habitCompletionRecords)
                .build();
    }
    public Habit toHabit(User user, HabitRequest request){
        return Habit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .archived(request.isArchived())
                .frequency(request.getFrequency())
                .startDate(request.getStartDate())
                .user(user)
                .build();
    }
    public Habit toHabit(Habit habit, HabitRequest request, User user){
        return habit.toBuilder()
                .name(request.getName())
                .description(request.getDescription())
                .archived(request.isArchived())
                .frequency(request.getFrequency())
                .startDate(request.getStartDate())
                .user(user)
                .build();
    }
}
