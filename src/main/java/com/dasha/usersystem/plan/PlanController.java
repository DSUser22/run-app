package com.dasha.usersystem.plan;
import com.dasha.usersystem.security.jwt.JWTUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController

@AllArgsConstructor
@RequestMapping(path = "api/my/plan")
public class PlanController {
    // для информации о плане
    private final PlanService planService;
    private final JWTUtility jwtUtility;

    @PutMapping(path = "create")
    public void createPlan(@RequestHeader(name="Authorization") String token, @RequestBody PlanRequest request){
        String username = getUsername(token);
        planService.savePlanInfo(username, request);
    }

    @GetMapping(path = "get")
    public PlanInfo getPlan(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        return planService.getPlanInfo(username);
    }

    /*@DeleteMapping(path = "delete")
    public void deletePlan(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        planService.deletePlanInfo(username);
    }*/

    String getUsername(String token){
        return jwtUtility.getUsernameFromToken(token.substring(7));
    }
}
