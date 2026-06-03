package pl.mazur.habittracker.task.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.goal.constant.GoalNotFoundException;
import pl.mazur.habittracker.goal.domain.Goal;
import pl.mazur.habittracker.goal.domain.GoalMapper;
import pl.mazur.habittracker.goal.domain.GoalRepository;
import pl.mazur.habittracker.goal.internal.dto.GoalDTO;
import pl.mazur.habittracker.habit.constant.HabitNotFoundException;
import pl.mazur.habittracker.task.constant.TaskNotFoundException;
import pl.mazur.habittracker.task.internal.dto.TaskDTO;
import pl.mazur.habittracker.task.internal.dto.TaskRequest;
import pl.mazur.habittracker.user.constant.UserNotFoundException;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.domain.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public List<TaskDTO> findAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId).stream()
                .map(taskMapper::toTaskDTO)
                .toList();
    }

    public TaskDTO findById(Long taskId) {
        return taskRepository.findById(taskId)
                .map(taskMapper::toTaskDTO)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public TaskDTO create(TaskRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        return taskMapper.toTaskDTO(
                taskRepository.save(taskMapper.toTask(request,user)));
    }

    public TaskDTO update(Long taskId, TaskRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        return taskRepository.findById(taskId)
                .map(task -> taskMapper.toTask(task,request,user))
                .map(taskRepository::save)
                .map(taskMapper::toTaskDTO)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public void delete(Long taskId) {
        Task task=taskRepository.findById(taskId)
                        .orElseThrow(() -> new TaskNotFoundException(taskId));
        taskRepository.delete(task);
    }

    public Optional<TaskDTO> complete(Long taskId){
        Task task=taskRepository.findById(taskId).orElseThrow(()->new TaskNotFoundException(taskId));
        System.out.println(task.isCompleted());
        if(task.isCompleted()){
            return Optional.empty();
        }
        else {
            return Optional.of(task)
                    .map(t -> t.toBuilder().completed(true).completedAt(LocalDateTime.now()).build())
                    .map(taskRepository::save)
                    .map(taskMapper::toTaskDTO);
        }
    }
}
