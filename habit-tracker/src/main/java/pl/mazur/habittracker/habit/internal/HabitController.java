package pl.mazur.habittracker.habit.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mazur.habittracker.habit.domain.HabitFacade;
import pl.mazur.habittracker.habit.internal.dto.*;

import java.util.List;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitFacade habitFacade;


    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<HabitDTO>> getHabitsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(habitFacade.findAllHabitsByUserId(userId));
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<HabitWithCompletionsDTO> getHabitById(@PathVariable Long habitId) {
        return ResponseEntity.ok(habitFacade.findHabitById(habitId));
    }

    @PostMapping
    public ResponseEntity<HabitDTO> createHabit(@RequestBody HabitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(habitFacade.createHabit(request));
    }

    @PutMapping("/{habitId}")
    public ResponseEntity<HabitDTO> updateHabit(
            @PathVariable Long habitId,
            @RequestBody HabitRequest request) {

        return ResponseEntity.ok(habitFacade.updateHabit(habitId, request));
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long habitId) {
        habitFacade.deleteHabit(habitId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/complete/{habitId}")
    public ResponseEntity<HabitCompletionDTO> createCompletion(
            @PathVariable Long habitId,
            @RequestBody HabitCompletionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(habitFacade.createCompletion(habitId, request));
    }

    @PutMapping("/completions/{completionId}")
    public ResponseEntity<HabitCompletionDTO> updateCompletion(
            @PathVariable Long completionId,
            @RequestBody HabitCompletionRequest request) {

        return ResponseEntity.ok(
                habitFacade.updateCompletion(completionId, request)
        );
    }

    @DeleteMapping("/completions/{completionId}")
    public ResponseEntity<Void> deleteCompletion(@PathVariable Long completionId) {
        habitFacade.deleteCompletion(completionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/{userId}")
    public ResponseEntity<HabitStatsDTO> getUserHabitStats(@PathVariable Long userId) {
        return ResponseEntity.ok(habitFacade.getHabitStats(userId));

    }

    @PostMapping("/streak/{habitId}")
    public ResponseEntity<Integer> getHabitStreak(@PathVariable Long habitId, @RequestBody HabitStreakRequest request){
        return ResponseEntity.ok(habitFacade.getHabitStreak(request,habitId));
    }
}
