package pl.mazur.habittracker.habit.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mazur.habittracker.habit.constant.HabitFrequency;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HabitWithCompletionsDTO {
    private Long id;

    private String name;

    private String description;

    private HabitFrequency frequency;

    private LocalDate startDate;

    private List<HabitCompletionDTO> completionRecords;

}