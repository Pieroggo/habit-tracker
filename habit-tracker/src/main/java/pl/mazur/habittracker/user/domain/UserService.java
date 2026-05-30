package pl.mazur.habittracker.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.user.constant.UserNotFoundException;
import pl.mazur.habittracker.user.internal.dto.UserDTO;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findById(Long userId){
        return userRepository.findById(userId)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
    public UserDTO create(UserRequest request){
        return userMapper.toUserDTO(
                userRepository.save(userMapper.toUser(request))
        );
    }
    public UserDTO update(Long userId, UserRequest request){
        return userRepository.findById(userId)
                .map(user -> userMapper.toUser(request))
                .map(userRepository::save)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
    public void delete(Long userId){
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }

}
