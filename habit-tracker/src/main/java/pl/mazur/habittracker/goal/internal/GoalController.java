package pl.mazur.habittracker.goal.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mazur.habittracker.goal.domain.GoalFacade;
import pl.mazur.habittracker.goal.internal.dto.*;
import pl.mazur.habittracker.habit.internal.dto.HabitStreakRequest;

import java.util.List;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalFacade goalFacade;

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<GoalDTO>> getGoalsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(goalFacade.findAllGoalsByUserId(userId));
    }


    @GetMapping("/{goalId}")
    public ResponseEntity<GoalWithProgressDTO> getGoalById(@PathVariable Long goalId) {
        return ResponseEntity.ok(goalFacade.findGoalById(goalId));
    }

    @PostMapping
    public ResponseEntity<GoalDTO> createGoal(@RequestBody GoalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(goalFacade.createGoal(request));
    }

    @PatchMapping("/{goalId}")
    public ResponseEntity<GoalDTO> updateGoal(
            @PathVariable Long goalId,
            @RequestBody GoalRequest request) {

        return ResponseEntity.ok(goalFacade.updateGoal(goalId, request));
    }


    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        goalFacade.deleteGoal(goalId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/progress/{goalId}")
    public ResponseEntity<GoalProgressDTO> createGoalProgress(
            @PathVariable Long goalId,
            @RequestBody GoalProgressRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(goalFacade.createGoalProgress(goalId, request));
    }

    @PatchMapping("/progress/{progressId}")
    public ResponseEntity<GoalProgressDTO> updateGoalProgress(
            @PathVariable Long progressId,
            @RequestBody GoalProgressRequest request) {

        return ResponseEntity.ok(
                goalFacade.updateGoalProgress(progressId, request)
        );
    }


    @DeleteMapping("/progress/{progressId}")
    public ResponseEntity<Void> deleteGoalProgress(@PathVariable Long progressId) {
        goalFacade.deleteGoalProgress(progressId);
        return ResponseEntity.noContent().build();
    }

}