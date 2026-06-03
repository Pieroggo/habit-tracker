package pl.mazur.habittracker.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mazur.habittracker.TestUtils;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.domain.UserFacade;
import pl.mazur.habittracker.user.domain.UserService;
import pl.mazur.habittracker.user.internal.dto.UserDTO;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacade userFacade;

    @Test
    void shouldFindUserById() {
        User user = UserFixtures.withCompleteData();
        UserDTO userDTO = UserDTOFixtures.withId(user.getId());

        when(userService.findById(user.getId()))
                .thenReturn(userDTO);

        UserDTO result = userFacade.findById(userDTO.getId());

        assertEquals(userDTO, result);

        verify(userService).findById(user.getId());
    }

    @Test
    void shouldUpdateUser() {
        Long userId = TestUtils.randomId();

        UserRequest request = UserRequestFixtures.withEmail("updated@mail.com");

        UserDTO userDTO = UserDTOFixtures.withEmailAndId("updated@mail.com",userId);

        when(userService.update(userId, request))
                .thenReturn(userDTO);

        UserDTO result = userFacade.update(userId, request);

        assertEquals(userDTO, result);

        verify(userService).update(userId, request);
    }

    @Test
    void shouldDeleteUser() {
        Long userId = TestUtils.randomId();

        doNothing().when(userService).delete(userId);

        userFacade.delete(userId);

        verify(userService).delete(userId);
    }
}
