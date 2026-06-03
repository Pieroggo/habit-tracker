package pl.mazur.habittracker.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mazur.habittracker.TestUtils;
import pl.mazur.habittracker.user.constant.UserNotFoundException;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.domain.UserMapper;
import pl.mazur.habittracker.user.domain.UserRepository;
import pl.mazur.habittracker.user.domain.UserService;
import pl.mazur.habittracker.user.internal.dto.UserDTO;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static pl.mazur.habittracker.user.UserFixtures.withEmail;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindUserById() {
        Long userId = TestUtils.randomId();

        User user = UserFixtures.withCompleteData();

        UserDTO dto = UserDTOFixtures.withCompleteData();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userMapper.toUserDTO(user))
                .thenReturn(dto);

        UserDTO result = userService.findById(userId);

        assertEquals(dto, result);

        verify(userRepository).findById(userId);
        verify(userMapper).toUserDTO(user);
    }

    @Test
    void shouldThrowWhenUserNotFoundById() {
        Long userId = TestUtils.randomId();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.findById(userId)
        );

        verify(userRepository).findById(userId);
        verifyNoInteractions(userMapper);
    }

    @Test
    void shouldUpdateUser() {
        Long userId = TestUtils.randomId();

        UserRequest request = UserRequestFixtures.withEmail("updated@mail.com");

        User existingUser = withEmail("updated@mail.com");

        User mappedUser = UserFixtures.withEmail("updated@mail.com");

        UserDTO dto = UserDTOFixtures.withEmail("updated@mail.com");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(existingUser));

        when(userMapper.toUser(request))
                .thenReturn(mappedUser);

        when(userRepository.save(mappedUser))
                .thenReturn(mappedUser);

        when(userMapper.toUserDTO(mappedUser))
                .thenReturn(dto);

        UserDTO result = userService.update(userId, request);

        assertEquals(dto, result);

        verify(userRepository).findById(userId);
        verify(userMapper).toUser(request);
        verify(userRepository).save(mappedUser);
        verify(userMapper).toUserDTO(mappedUser);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingUser() {
        Long userId = TestUtils.randomId();

        UserRequest request = UserRequestFixtures.withCompleteData();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.update(userId, request)
        );

        verify(userRepository).findById(userId);
        verifyNoInteractions(userMapper);
    }

    @Test
    void shouldDeleteUser() {
        Long userId = TestUtils.randomId();

        User user = UserFixtures.withCompleteData();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingUser() {
        Long userId = TestUtils.randomId();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.delete(userId)
        );

        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any());
    }
}
