package pl.mazur.habittracker.user.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.domain.UserMapper;
import pl.mazur.habittracker.user.domain.UserRepository;
import pl.mazur.habittracker.user.internal.dto.LoginRequest;
import pl.mazur.habittracker.user.internal.dto.RegisterResponse;
import pl.mazur.habittracker.user.internal.dto.TokenResponse;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(UserRequest request) {
        User user = userMapper.toUser(request);
        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new RegisterResponse(token,user.getId());
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token =
                jwtService.generateToken(
                        request.getEmail()
                );

        return new TokenResponse(token);
    }
}
