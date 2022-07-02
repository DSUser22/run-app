package com.dasha.usersystem.plan;
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
    private final JWTUtility jwtUtility;

    @PostMapping()
    public void createPlan(@RequestHeader(name="Authorization") String token, @RequestBody PlanRequest request){
        planService.savePlan(jwtUtility.getIdFromToken(token), request);
    }

    @GetMapping()
    public Plan getPlan(@RequestHeader(name="Authorization") String token){
        return planService.findPlanByAppUserId(jwtUtility.getIdFromToken(token));
    }

    @DeleteMapping()
    public void deletePlan(@RequestHeader(name="Authorization") String token){
        planService.deletePlanByAppUserId(jwtUtility.getIdFromToken(token));
    }
}
