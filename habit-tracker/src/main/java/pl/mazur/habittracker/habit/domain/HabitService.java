package pl.mazur.habittracker.habit.domain;

import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.habit.constant.HabitNotFoundException;
import pl.mazur.habittracker.habit.domain.Habit;
import pl.mazur.habittracker.habit.domain.HabitMapper;
import pl.mazur.habittracker.habit.domain.HabitRepository;
import pl.mazur.habittracker.habit.internal.dto.*;
import pl.mazur.habittracker.habit.constant.HabitNotFoundException;
import pl.mazur.habittracker.habit.domain.Habit;
import pl.mazur.habittracker.habit.domain.HabitMapper;
import pl.mazur.habittracker.habit.domain.HabitRepository;
import pl.mazur.habittracker.habit.internal.dto.HabitStatsDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitDTO;
import pl.mazur.habittracker.habit.internal.dto.HabitRequest;
import pl.mazur.habittracker.user.constant.UserNotFoundException;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.domain.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final HabitMapper habitMapper;
    private final UserRepository userRepository;

    public List<HabitDTO> findAllByUserId(Long userId) {
        return habitRepository.findAllByUserId(userId).stream()
                .map(habitMapper::toHabitDTO)
                .toList();
    }

    public HabitDTO findById(Long habitId) {
        return habitRepository.findById(habitId)
                .map(habitMapper::toHabitDTO)
                .orElseThrow(() -> new HabitNotFoundException(habitId));
    }

    public HabitDTO create(HabitRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        return habitMapper.toHabitDTO(
                habitRepository.save(habitMapper.toHabit(user, request))
        );
    }

    public HabitDTO update(Long habitId, HabitRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        return habitRepository.findById(habitId)
                .map(habit -> habitMapper.toHabit(habit, request, user))
                .map(habitRepository::save)
                .map(habitMapper::toHabitDTO)
                .orElseThrow(() -> new HabitNotFoundException(habitId));
    }

    public void delete(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitNotFoundException(habitId));
        habitRepository.delete(habit);
    }

}