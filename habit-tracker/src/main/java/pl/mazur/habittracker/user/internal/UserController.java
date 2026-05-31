package pl.mazur.habittracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.mazur.habittracker.user.domain.UserFacade;
import pl.mazur.habittracker.user.internal.dto.UserDTO;
import pl.mazur.habittracker.user.internal.dto.UserRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userFacade.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,@RequestBody UserRequest request){
        return ResponseEntity.ok(userFacade.update(id,request));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
       userFacade.delete(id);
       return ResponseEntity.noContent().build();
    }
    //add functionality for user to only find/update/delete their own users
}
