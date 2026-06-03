package pl.mazur.habittracker.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mazur.habittracker.TestUtils;
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
        Long userId = TestUtils.randomId();
        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskService.findAllByUserId(userId)).thenReturn(List.of(dto));

        List<TaskDTO> result = taskFacade.findAllTasksByUserId(userId);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        verify(taskService).findAllByUserId(userId);
    }


    @Test
    void shouldReturnTaskById() {
        Long taskId = TestUtils.randomId();

        TaskDTO dto = TaskDTOFixtures.withTaskId(taskId);

        when(taskService.findById(taskId)).thenReturn(dto);

        TaskDTO result = taskFacade.findTaskById(taskId);

        assertEquals(dto, result);

        verify(taskService).findById(taskId);
    }


    @Test
    void shouldCreateTask() {
        TaskRequest request = TaskRequestFixtures.withUserId(TestUtils.randomId());

        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskService.create(request)).thenReturn(dto);

        TaskDTO result = taskFacade.createTask(request);

        assertEquals(dto, result);

        verify(taskService).create(request);
    }


    @Test
    void shouldUpdateTask() {
        Long taskId = TestUtils.randomId();
        TaskRequest request = TaskRequestFixtures.withCompleteData();

        TaskDTO dto = TaskDTOFixtures.withTaskId(TestUtils.randomId());

        when(taskService.update(taskId, request)).thenReturn(dto);

        TaskDTO result = taskFacade.updateTask(taskId, request);

        assertEquals(dto, result);

        verify(taskService).update(taskId, request);
    }


    @Test
    void shouldDeleteTask() {
        Long taskId = TestUtils.randomId();

        taskFacade.deleteTask(taskId);

        verify(taskService).delete(taskId);
    }
}
