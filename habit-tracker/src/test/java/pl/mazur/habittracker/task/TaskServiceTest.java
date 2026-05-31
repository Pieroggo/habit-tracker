package pl.mazur.habittracker.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mazur.habittracker.task.constant.TaskNotFoundException;
import pl.mazur.habittracker.task.domain.Task;
import pl.mazur.habittracker.task.domain.TaskMapper;
import pl.mazur.habittracker.task.domain.TaskRepository;
import pl.mazur.habittracker.task.domain.TaskService;
import pl.mazur.habittracker.task.internal.dto.TaskDTO;
import pl.mazur.habittracker.task.internal.dto.TaskRequest;
import pl.mazur.habittracker.user.constant.UserNotFoundException;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.domain.UserRepository;
import pl.mazur.habittracker.user.UserFixtures;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnAllTasksByUserId() {
        Long userId = 1L;

        Task task = TaskFixtures.withCompleteData();
        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskRepository.findAllByUserId(userId)).thenReturn(List.of(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(dto);

        List<TaskDTO> result = taskService.findAllByUserId(userId);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        verify(taskRepository).findAllByUserId(userId);
        verify(taskMapper).toTaskDTO(task);
    }


    @Test
    void shouldReturnTaskById() {
        Long taskId = 1L;

        Task task = TaskFixtures.withCompleteData();
        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(dto);

        TaskDTO result = taskService.findById(taskId);

        assertEquals(dto, result);

        verify(taskRepository).findById(taskId);
        verify(taskMapper).toTaskDTO(task);
    }

    @Test
    void shouldThrowWhenTaskNotFoundById() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.findById(taskId));

        verify(taskRepository).findById(taskId);
    }

    @Test
    void shouldCreateTask() {
        User user = UserFixtures.withCompleteData();
        TaskRequest request = TaskRequestFixtures.withUserId(user.getId());


        Task task = TaskFixtures.withCompleteData();
        Task savedTask = TaskFixtures.withCompleteData();
        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskMapper.toTask(request, user)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(savedTask);
        when(taskMapper.toTaskDTO(savedTask)).thenReturn(dto);

        TaskDTO result = taskService.create(request);

        assertEquals(dto, result);

        verify(userRepository).findById(user.getId());
        verify(taskRepository).save(task);
        verify(taskMapper).toTask(request, user);
    }

    @Test
    void shouldThrowWhenUserNotFoundDuringCreate() {
        Long userId = 1L;

        TaskRequest request = TaskRequestFixtures.withUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> taskService.create(request));

        verify(userRepository).findById(userId);
        verifyNoInteractions(taskRepository);
    }


    @Test
    void shouldUpdateTask() {
        User user = UserFixtures.withCompleteData();
        TaskRequest request = TaskRequestFixtures.withUserId(user.getId());


        Task existingTask = TaskFixtures.withCompleteData();
        Task savedTask = TaskFixtures.withTaskCompleted();

        TaskDTO dto = TaskDTOFixtures.withTaskCompleted();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));
        when(taskMapper.toTask(existingTask, request, user)).thenReturn(savedTask);
        when(taskRepository.save(savedTask)).thenReturn(savedTask);
        when(taskMapper.toTaskDTO(savedTask)).thenReturn(dto);

        TaskDTO result = taskService.update(existingTask.getId(), request);

        assertEquals(dto, result);

        verify(taskRepository).findById(existingTask.getId());
        verify(taskRepository).save(savedTask);
    }

    @Test
    void shouldThrowWhenTaskNotFoundDuringUpdate() {
        Long taskId=1L;
        User user = UserFixtures.withCompleteData();
        TaskRequest request = TaskRequestFixtures.withUserId(user.getId());


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.update(taskId, request));
    }

    @Test
    void shouldThrowWhenUserNotFoundDuringUpdate() {
        Long taskId = 1L;
        Long userId = 1L;

        TaskRequest request = TaskRequestFixtures.withUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> taskService.update(taskId, request));

        verifyNoInteractions(taskRepository);
    }


    @Test
    void shouldDeleteTask() {
        Long taskId = 1L;

        Task task = TaskFixtures.withCompleteData();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.delete(taskId);

        verify(taskRepository).delete(task);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingTask() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.delete(taskId));

        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }
}
