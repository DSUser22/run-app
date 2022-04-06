package com.dasha.usersystem.training;

import com.dasha.usersystem.plan.Plan;
import com.dasha.usersystem.plan.PlanService;
import com.dasha.usersystem.security.jwt.JWTUtility;
import com.dasha.usersystem.training.type.running.Running;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
@RequestMapping(path = "api/v1/my/plan/training")
@RestController
public class TrainingController {
    private final JWTUtility jwtUtility;
    private final PlanService planService;
    private final TrainingService trainingService;

    @DeleteMapping(path = "delete")
    public void deleteTrainings(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        Plan plan = planService.getPlan(username);
        trainingService.deleteAllByPlanId(plan.getId());
    }

    @GetMapping(path = "get")
    public Training getTraining(
            @RequestHeader(name="Authorization") String token,
            @RequestParam("number") @Min(1) int number){
        String username = getUsername(token);
        Plan plan = planService.getPlan(username);
        Training tr = trainingService.findTrainingByPlanInfoIdAndTrainingNumber(
                plan.getId(), number);
        return tr;
    }

    @GetMapping(path = "getrunning")
    public Running getRunning(
            @RequestHeader(name="Authorization") String token,
            @RequestParam("number") @Min(1) int number){
        return getTraining(token, number).getRunning();
    }

    @GetMapping(path = "getall")
    public List<Training> getAllTrainings(
            @RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        Plan plan = planService.getPlan(username);
        return trainingService.getAllTrainings(plan.getId());
    }

    // сделать тренировку выполненной
    @PutMapping(path = "done")
    public void isDoneTraining(@RequestHeader(name="Authorization") String token,
                               @RequestParam("number") @Min(1) int number){
        String username = getUsername(token);
        Long planId = planService.getPlan(username).getId();
        trainingService.isDoneTraining(planId, number);
    }

    String getUsername(String token){
        return jwtUtility.getUsernameFromToken(token.substring(7));
    }
}
