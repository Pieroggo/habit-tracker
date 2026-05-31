package pl.mazur.habittracker.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mazur.habittracker.task.domain.TaskFacade;
import pl.mazur.habittracker.task.domain.TaskService;
import pl.mazur.habittracker.task.internal.dto.TaskDTO;
import pl.mazur.habittracker.task.internal.dto.TaskRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskFacadeTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskFacade taskFacade;


    @Test
    void shouldReturnAllTasksByUserId() {
        Long userId = 1L;
        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskService.findAllByUserId(userId)).thenReturn(List.of(dto));

        List<TaskDTO> result = taskFacade.findAllTasksByUserId(userId);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        verify(taskService).findAllByUserId(userId);
    }


    @Test
    void shouldReturnTaskById() {
        Long taskId = 1L;

        TaskDTO dto = TaskDTOFixtures.withTaskId(taskId);

        when(taskService.findById(taskId)).thenReturn(dto);

        TaskDTO result = taskFacade.findTaskById(taskId);

        assertEquals(dto, result);

        verify(taskService).findById(taskId);
    }


    @Test
    void shouldCreateTask() {
        TaskRequest request = TaskRequestFixtures.withUserId(1L);

        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskService.create(request)).thenReturn(dto);

        TaskDTO result = taskFacade.createTask(request);

        assertEquals(dto, result);

        verify(taskService).create(request);
    }


    @Test
    void shouldUpdateTask() {
        Long taskId = 1L;
        TaskRequest request = TaskRequestFixtures.withCompleteData();

        TaskDTO dto = TaskDTOFixtures.withTaskId(1L);

        when(taskService.update(taskId, request)).thenReturn(dto);

        TaskDTO result = taskFacade.updateTask(taskId, request);

        assertEquals(dto, result);

        verify(taskService).update(taskId, request);
    }


    @Test
    void shouldDeleteTask() {
        Long taskId = 1L;

        taskFacade.deleteTask(taskId);

        verify(taskService).delete(taskId);
    }
}
