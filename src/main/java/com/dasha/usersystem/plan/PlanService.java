package com.dasha.usersystem.plan;

import com.dasha.usersystem.plan.creating.MarathonDateValidator;
import com.dasha.usersystem.plan.creating.TrainingFactoryService;
import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PlanService {
    private final PlanRepo planRepo;
    private final AppUserRepo appUserRepo;
    private final TrainingFactoryService trainingFactoryService;
    private final MarathonDateValidator marathonDateValidator;

    @Transactional
    public void savePlan(Long userId, PlanRequest request){
        AppUser appUser = appUserRepo.findById(userId).orElseThrow(
                () ->new IllegalStateException("user not found"));
        Plan plan = new Plan(appUser, request);
        if(!marathonDateValidator.test(plan.getCountOfWeeks())){
            throw new IllegalStateException("very few weeks");
        }
        if(planRepo.findPlanByAppUserId(userId).isPresent()){
            throw new IllegalStateException("plan is already exists");
        }
        plan.setTrainings(trainingFactoryService.createTrainings(plan));
        planRepo.save(plan);
    }

    @Transactional
    public Plan findPlanByAppUserId(Long userId){
        return planRepo.findPlanByAppUserId(userId).orElseThrow(()->new IllegalStateException("plan not found"));
    }

    @Transactional
    public void deletePlanByAppUserId(Long userId) {
        Long planId = planRepo.findPlanByAppUserId(userId).orElseThrow(IllegalStateException::new).getId();
        planRepo.deleteById(planId);
        planRepo.flush();
    }

}
