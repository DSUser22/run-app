package com.dasha.usersystem.plan;
import com.dasha.usersystem.training.TrainingService;
import com.dasha.usersystem.security.jwt.JWTUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/my/plan")
@CrossOrigin("*")
public class PlanController {
    // для информации о плане
    private final PlanService planService;
    private final TrainingService trainingService;
    private final JWTUtility jwtUtility;

    @PostMapping(path = "post")
    public void createPlan(@RequestHeader(name="Authorization") String token, @RequestBody PlanRequest request){
        String username = getUsername(token);
        planService.savePlan(username, request);
    }

    @GetMapping(path = "get")
    public Plan getPlan(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        return planService.getPlan(username);
    }

    @DeleteMapping(path = "delete")
    public void deletePlan(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        trainingService.deleteAllByPlanId(planService.getPlan(username).getId());
        planService.deletePlan(username);
    }

    String getUsername(String token){
        return jwtUtility.getUsernameFromToken(token.substring(7));
    }
}
