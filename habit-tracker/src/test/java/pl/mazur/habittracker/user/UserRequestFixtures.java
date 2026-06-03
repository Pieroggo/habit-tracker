package pl.mazur.habittracker.user;

import pl.mazur.habittracker.user.constant.UserRole;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

import java.time.LocalDateTime;

public class UserRequestFixtures {
    public static UserRequest.UserRequestBuilder baseUser() {
        return UserRequest.builder()
                .email("test@example.com")
                .password("password");
    }

    public static UserRequest withCompleteData() {
        return baseUser().build();
    }

    public static UserRequest withEmail(String email){
        return baseUser().email(email).build();
    }

}
