package pl.mazur.habittracker.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import pl.mazur.habittracker.task.domain.TaskFacade;
import pl.mazur.habittracker.task.internal.dto.TaskDTO;
import pl.mazur.habittracker.task.internal.dto.TaskRequest;
import pl.mazur.habittracker.user.auth.HabitTrackerUserDetailsService;
import pl.mazur.habittracker.user.auth.JwtService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(controllers = TaskController.class)
@ImportAutoConfiguration(exclude = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        })
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private HabitTrackerUserDetailsService userDetailsService;

    @MockBean
    private TaskFacade taskFacade;

    // ---------------- GET ALL ----------------

    @Test
    void shouldGetTasksByUser() throws Exception {
        Long userId = 1L;

        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskFacade.findAllTasksByUserId(userId))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/task/user/{userId}",userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(dto.getId()))
                .andExpect(jsonPath("$[0].title").value(dto.getTitle()));

        verify(taskFacade).findAllTasksByUserId(userId);
    }

    // ---------------- GET BY ID ----------------

    @Test
    void shouldGetTaskById() throws Exception {
        Long taskId = 1L;

        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskFacade.findTaskById(taskId)).thenReturn(dto);

        mockMvc.perform(get("/task/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.title").value(dto.getTitle()));

        verify(taskFacade).findTaskById(taskId);
    }

    // ---------------- CREATE ----------------

    @Test
    void shouldCreateTask() throws Exception {
        Long userId = 1L;
        TaskRequest request = TaskRequestFixtures.withUserId(userId);

        TaskDTO dto = TaskDTOFixtures.withCompleteData();

        when(taskFacade.createTask(any(TaskRequest.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.title").value(dto.getTitle()));

        verify(taskFacade).createTask(any(TaskRequest.class));
    }

    // ---------------- UPDATE ----------------

    @Test
    void shouldUpdateTask() throws Exception {
        Long taskId = 1L;
        Long userId = 1L;

        TaskRequest request = TaskRequestFixtures.withUserIdAndCompleted(userId);

        TaskDTO dto = TaskDTOFixtures.withTaskCompleted();

        when(taskFacade.updateTask(eq(taskId), any(TaskRequest.class)))
                .thenReturn(dto);

        mockMvc.perform(patch("/task/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.completed").value(true));

        verify(taskFacade).updateTask(eq(taskId), any(TaskRequest.class));
    }

    // ---------------- DELETE ----------------

    @Test
    void shouldDeleteTask() throws Exception {
        Long taskId = 1L;

        doNothing().when(taskFacade).deleteTask(taskId);

        mockMvc.perform(delete("/task/{taskId}", taskId))
                .andExpect(status().isNoContent());

        verify(taskFacade).deleteTask(taskId);
    }
}
