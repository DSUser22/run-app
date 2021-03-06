package com.dasha.usersystem.training;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingService {
    private final TrainingRepo trainingRepo;

    public Training findTrainingByAppUserIdAndNumber(Long userId, Integer trainingNumber){
        return trainingRepo.findTrainingByAppUserIdAndNumber(userId, trainingNumber)
                .orElseThrow(()->new IllegalStateException("training not found"));
    }
    public Training findTrainingByAppUserIdAndDate(Long userId, String date){
        return trainingRepo.findTrainingByAppUserIdAndDate(userId, LocalDate.parse(date))
                .orElseThrow(()->new IllegalStateException("training not found"));
    }
    public int updateIsDone(Long userId, Integer number){
        return trainingRepo.updateIsDone(userId, number, true);
    }

    public List<Training> getAllTrainings(Long userId){
        return trainingRepo.findAllByAppUserId(userId);
    }
}
