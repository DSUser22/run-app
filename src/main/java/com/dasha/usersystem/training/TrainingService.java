package com.dasha.usersystem.training;
import com.dasha.usersystem.training.type.RunningRepo;
import com.dasha.usersystem.training.type.running.Running;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainingService {
    private final TrainingRepo trainingRepo;
    private final RunningRepo runningRepo;


    public Training findTrainingByPlanInfoIdAndTrainingNumber(Long id, Integer trainingNumber){
        return trainingRepo.findTrainingByPlanIdAndTrainingNumber(id, trainingNumber)
                .orElseThrow(()->new IllegalStateException("training not found"));
    }

    @Transactional
    public void deleteAllByPlanId(Long id){
        trainingRepo.deleteAllByPlanId(id);
    }

    @Transactional
    public void saveRunning(Running running){
        runningRepo.save(running);
    }
    @Transactional
    public void saveTraining(Training training){
        trainingRepo.save(training);
    }

    @Transactional
    public List<Training> getAllTrainings(Long id){
        return trainingRepo.findAllByPlanId(id);
    }
    @Transactional
    public void isDoneTraining(Long planId, int trainingId){

        trainingRepo.isDoneTraining(planId, trainingId);
    }
}
