package pl.mazur.habittracker.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import pl.mazur.habittracker.user.auth.AuthenticationService;
import pl.mazur.habittracker.user.auth.HabitTrackerUserDetailsService;
import pl.mazur.habittracker.user.auth.JwtService;
import pl.mazur.habittracker.user.internal.AuthenticationController;
import pl.mazur.habittracker.user.internal.dto.LoginRequest;
import pl.mazur.habittracker.user.internal.dto.TokenResponse;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ImportAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private HabitTrackerUserDetailsService userDetailsService;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    void shouldReturnJwtToken_whenCredentialsAreValid() throws Exception{
        LoginRequest request = LoginRequestFixtures.withCompleteData();

        String fakeJwt = "eyJhbGciOiJIUzI1NiJ9.fake.token";

        when(authenticationService.login(any(LoginRequest.class)))
                .thenReturn(new TokenResponse(fakeJwt));


            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value(fakeJwt));

    }

    @Test
    void shouldReturnUnauthorized_whenCredentialsAreInvalid() throws Exception {
        LoginRequest request = new LoginRequest("wrong", "wrong");

        when(authenticationService.login(any(LoginRequest.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));


        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}