package pl.mazur.habittracker.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mazur.habittracker.user.internal.dto.UserDTO;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
    public User toUser(UserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }

    public User toUser(User user, UserRequest userRequest){
        return user.toBuilder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }
}
