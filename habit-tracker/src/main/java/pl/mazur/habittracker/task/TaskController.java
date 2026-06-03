package pl.mazur.habittracker.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mazur.habittracker.task.domain.TaskFacade;
import pl.mazur.habittracker.task.internal.dto.TaskCompletedResponse;
import pl.mazur.habittracker.task.internal.dto.TaskDTO;
import pl.mazur.habittracker.task.internal.dto.TaskRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskFacade taskFacade;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskFacade.findAllTasksByUserId(userId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskFacade.findTaskById(taskId));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskFacade.createTask(request));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequest request) {

        return ResponseEntity.ok(taskFacade.updateTask(taskId, request));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskFacade.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/complete/{taskId}")
    public ResponseEntity<?> completeTask(@PathVariable Long taskId){
        return taskFacade.completeTask(taskId)
                .map(task -> new TaskCompletedResponse(task,"Task completed successfully"))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                .badRequest()
                .body(new TaskCompletedResponse(null, "Task was already completed")));

    }
}
