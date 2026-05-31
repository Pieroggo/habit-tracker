package pl.mazur.habittracker.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mazur.habittracker.user.internal.dto.UserDTO;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserDTO findById(Long userId){
        return userService.findById(userId);
    }

    public UserDTO update(Long userId, UserRequest request){
        return userService.update(userId,request);
    }

    public void delete(Long userId){
        userService.delete(userId);
    }
}
