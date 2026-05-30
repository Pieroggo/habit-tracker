package pl.mazur.habittracker.goal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mazur.habittracker.goal.constant.GoalNotFoundException;
import pl.mazur.habittracker.goal.internal.dto.GoalDTO;
import pl.mazur.habittracker.goal.internal.dto.GoalRequest;
import pl.mazur.habittracker.user.constant.UserNotFoundException;
import pl.mazur.habittracker.user.domain.User;
import pl.mazur.habittracker.user.domain.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {
   private final GoalRepository goalRepository;
   private final GoalMapper goalMapper;
   private final UserRepository userRepository;

   public List<GoalDTO> findAllByUserId(Long userId){
       return goalRepository.findAllByUserId(userId).stream()
               .map(goalMapper::toGoalDTO)
               .toList();
   }
   public GoalDTO findById(Long goalId){
       return goalRepository.findById(goalId)
               .map(goalMapper::toGoalDTO)
               .orElseThrow(() -> new GoalNotFoundException(goalId));
   }
    public GoalDTO create(GoalRequest request){
       User user =userRepository.findById(request.getUserId())
               .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        return goalMapper.toGoalDTO(
                goalRepository.save(goalMapper.toGoal(request,user))
        );
    }
    public GoalDTO update(Long goalId, GoalRequest request){
        User user =userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        return goalRepository.findById(goalId)
                .map(goal -> goalMapper.toGoal(request,user))
                .map(goalRepository::save)
                .map(goalMapper::toGoalDTO)
                .orElseThrow(() -> new GoalNotFoundException(goalId));
    }
    public void delete(Long goalId){
        Goal goal=goalRepository.findById(goalId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));
        goalRepository.delete(goal);
    }

}
