package com.dasha.usersystem.training;

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
    private final TrainingService trainingService;

    @GetMapping(path = "get")
    public Training findTrainingByNumber(
            @RequestHeader(name="Authorization") String token,
            @RequestParam("number") @Min(1) int number){
        Long userId = jwtUtility.getIdFromToken(token);
        Training t = trainingService.findTrainingByAppUserIdAndNumber(userId, number);
        return t;
    }
    @GetMapping()
    public Training findTrainingByDate(
            @RequestHeader(name="Authorization") String token,
            @RequestBody DateRequest request){
        Long userId = jwtUtility.getIdFromToken(token);
        Training t = trainingService.findTrainingByAppUserIdAndDate(userId, request.getDate());
        return t;
    }

    @GetMapping(path = "running")
    public Running getRunning(
            @RequestHeader(name="Authorization") String token,
            @RequestParam("number") @Min(1) int number){
        Long userId = jwtUtility.getIdFromToken(token);
        return trainingService.findTrainingByAppUserIdAndNumber(userId, number).getRunning();
    }

    @GetMapping(path = "all")
    public List<Training> getAllTrainings(
            @RequestHeader(name="Authorization") String token){
        Long userId = jwtUtility.getIdFromToken(token);
        return trainingService.getAllTrainings(userId);
    }
    @PutMapping()
    public void isDoneTraining(@RequestHeader(name="Authorization") String token,
                               @RequestParam("number") @Min(1) int number){
        Long userId = jwtUtility.getIdFromToken(token);
        trainingService.updateIsDone(userId, number);
    }
}
