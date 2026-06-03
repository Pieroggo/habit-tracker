package pl.mazur.habittracker.habit;

import pl.mazur.habittracker.habit.constant.HabitFrequency;
import pl.mazur.habittracker.habit.domain.Habit;
import pl.mazur.habittracker.user.UserFixtures;

import java.time.LocalDate;

public class HabitFixtures {

    public static Habit.HabitBuilder baseHabit() {
        return Habit.builder()
                .id(1L)
                .name("Drink Water")
                .description("Drink at least 2L of water daily")
                .frequency(HabitFrequency.DAILY)
                .startDate(LocalDate.now())
                .user(UserFixtures.withCompleteData());
    }

    public static Habit withCompleteData() {
        return baseHabit().build();
    }
}
