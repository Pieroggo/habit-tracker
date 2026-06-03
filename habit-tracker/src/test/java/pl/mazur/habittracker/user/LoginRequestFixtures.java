package pl.mazur.habittracker.user;

import pl.mazur.habittracker.user.internal.dto.LoginRequest;

public class LoginRequestFixtures {

    public static LoginRequest.LoginRequestBuilder baseLoginRequest() {
        return LoginRequest.builder()
                .email("test@example.com")
                .password("password123");
    }

    public static LoginRequest withCompleteData() {
        return baseLoginRequest().build();
    }
}
