package pl.mazur.habittracker.user;

import pl.mazur.habittracker.user.constant.UserRole;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.internal.dto.UserDTO;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

import java.time.LocalDateTime;

public class UserDTOFixtures {
    public static UserDTO.UserDTOBuilder baseUser() {
        return UserDTO.builder()
                .id(1L)
                .email("test@example.com")
                .role(UserRole.ADMIN)
                .createdAt(LocalDateTime.now());
    }

    public static UserDTO withCompleteData() {
        return baseUser().build();
    }

    public static UserDTO withEmail(String email){
        return baseUser().email(email).build();
    }

    public static UserDTO withId(Long id){
        return baseUser().id(id).build();
    }

    public static UserDTO withEmailAndId(String email,Long id){
        return baseUser().email(email).id(id).build();
    }

}
