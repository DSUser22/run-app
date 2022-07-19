package com.dasha.usersystem.plan;
import com.dasha.usersystem.security.jwt.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/my/plan")
@CrossOrigin("*")
public class PlanController {
    private final PlanService planService;
    private final JwtUtility jwtUtility;

    @PostMapping()
    public ResponseEntity<?> createPlan(@RequestHeader(name="Authorization") String token, @RequestBody PlanRequest request){
        planService.savePlan(jwtUtility.getIdFromJwtToken(token), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<Plan> getPlan(@RequestHeader(name="Authorization") String token){
        return ResponseEntity.ok(planService.findPlanByAppUserId(jwtUtility.getIdFromJwtToken(token)));
    }

    @DeleteMapping()
    public ResponseEntity<?> deletePlan(@RequestHeader(name="Authorization") String token){
        planService.deletePlanByAppUserId(jwtUtility.getIdFromJwtToken(token));
        return ResponseEntity.ok().build();
    }
}
