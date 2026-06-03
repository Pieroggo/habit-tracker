package pl.mazur.habittracker.user;

import pl.mazur.habittracker.user.constant.UserRole;
import pl.mazur.habittracker.user.domain.User;

import java.time.LocalDateTime;

public class UserFixtures {
    public static User.UserBuilder baseUser() {
        return User.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .role(UserRole.ADMIN)
                .createdAt(LocalDateTime.now());
    }

    public static User withCompleteData() {
        return baseUser().build();
    }

    public static User withEmail(String email){
        return baseUser().email(email).build();
    }
}
