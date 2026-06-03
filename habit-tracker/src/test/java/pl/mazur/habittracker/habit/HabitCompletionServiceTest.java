package pl.mazur.habittracker.habit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mazur.habittracker.TestUtils;
import pl.mazur.habittracker.habit.domain.*;
import pl.mazur.habittracker.habit.internal.dto.HabitStatsDTO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitCompletionServiceTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private HabitCompletionRepository habitCompletionRepository;

    @InjectMocks
    private HabitCompletionService habitCompletionService;

    @Test
    void shouldReturnZeroStatsWhenNoCompletions() {
        Long userId =  TestUtils.randomId();

        Habit habit = HabitFixtures.withCompleteData();

        when(habitRepository.findAllByUserId(userId))
                .thenReturn(List.of(habit));

        when(habitCompletionRepository.findAllByHabitId(habit.getId()))
                .thenReturn(Collections.emptyList());

        HabitStatsDTO result = habitCompletionService.getUserStats(userId);

        assertEquals(0.0, result.getCompletionRate());
        assertEquals(0L, result.getTotalCompletions());
        assertNull(result.getFavouriteHabitName());
    }

    @Test
    void shouldCalculateBasicStatsCorrectly() {
        Long userId = TestUtils.randomId();

        Habit habit = HabitFixtures.withCompleteData();

        HabitCompletion c1 = HabitCompletion.builder()
                .completedAt(LocalDateTime.now().minusDays(2))
                .habit(habit)
                .build();

        HabitCompletion c2 = HabitCompletion.builder()
                .completedAt(LocalDateTime.now().minusDays(1))
                .habit(habit)
                .build();

        HabitCompletion c3 = HabitCompletion.builder()
                .completedAt(LocalDateTime.now())
                .habit(habit)
                .build();

        when(habitRepository.findAllByUserId(userId))
                .thenReturn(List.of(habit));

        when(habitCompletionRepository.findAllByHabitId(habit.getId()))
                .thenReturn(List.of(c1, c2, c3));

        HabitStatsDTO result = habitCompletionService.getUserStats(userId);

        assertEquals(3L, result.getTotalCompletions());
        assertNotNull(result.getFavouriteHabitName());
        assertEquals(habit.getName(), result.getFavouriteHabitName());
        assertTrue(result.getCompletionRate() > 0);
    }

    @Test
    void shouldReturnZeroStreakWhenNoCompletions() {
        Long habitId = TestUtils.randomId();

        when(habitCompletionRepository.findByHabitIdOrderByCompletedAtDesc(habitId))
                .thenReturn(Collections.emptyList());

        int streak = habitCompletionService.getCurrentStreak(habitId, 2);

        assertEquals(0, streak);
    }

    @Test
    void shouldReturnFullStreakWhenNoBreaks() {
        Long habitId = TestUtils.randomId();

        Habit habit = Habit.builder().id(habitId).name("Test").build();

        List<HabitCompletion> completions = List.of(
                HabitCompletion.builder()
                        .habit(habit)
                        .completedAt(LocalDateTime.now())
                        .build(),
                HabitCompletion.builder()
                        .habit(habit)
                        .completedAt(LocalDateTime.now().minusDays(1))
                        .build(),
                HabitCompletion.builder()
                        .habit(habit)
                        .completedAt(LocalDateTime.now().minusDays(2))
                        .build()
        );

        when(habitCompletionRepository.findByHabitIdOrderByCompletedAtDesc(habitId))
                .thenReturn(completions);

        int streak = habitCompletionService.getCurrentStreak(habitId, 0);

        assertTrue(streak >= 3);
    }
}
