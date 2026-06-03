package pl.mazur.habittracker.task.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mazur.habittracker.task.domain.Task;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCompletedResponse {
    private TaskDTO task;
    private String message;
}
