package com.dasha.usersystem.training;

import com.dasha.usersystem.security.jwt.JwtUtility;
import com.dasha.usersystem.training.type.running.Running;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
@RequestMapping(path = "api/v1/my/plan/training")
@RestController
public class TrainingController {
    private final JwtUtility jwtUtility;
    private final TrainingService trainingService;

    @GetMapping(path = "get")
    public ResponseEntity<Training> findTrainingByNumber(
            @RequestHeader(name="Authorization") String token,
            @RequestParam("number") @Min(1) int number){
        Long userId = jwtUtility.getIdFromJwtToken(token);
        return ResponseEntity.ok(trainingService.findTrainingByAppUserIdAndNumber(userId, number));
    }
    @GetMapping()
    public ResponseEntity<Training> findTrainingByDate(
            @RequestHeader(name="Authorization") String token,
            @RequestBody DateRequest request){
        Long userId = jwtUtility.getIdFromJwtToken(token);
        return ResponseEntity.ok(trainingService.findTrainingByAppUserIdAndDate(userId, request.getDate()));
    }

    @GetMapping(path = "running")
    public ResponseEntity<Running> getRunning(
            @RequestHeader(name="Authorization") String token,
            @RequestParam("number") @Min(1) int number){
        Long userId = jwtUtility.getIdFromJwtToken(token);
        return ResponseEntity.ok(trainingService.findTrainingByAppUserIdAndNumber(userId, number).getRunning());
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Training>> getAllTrainings(
            @RequestHeader(name="Authorization") String token){
        Long userId = jwtUtility.getIdFromJwtToken(token);
        return ResponseEntity.ok(trainingService.getAllTrainings(userId));
    }
    @PutMapping()
    public ResponseEntity<?> isDoneTraining(@RequestHeader(name="Authorization") String token,
                               @RequestParam("number") @Min(1) int number){
        Long userId = jwtUtility.getIdFromJwtToken(token);
        trainingService.updateIsDone(userId, number);
        return ResponseEntity.ok().build();
    }
}
