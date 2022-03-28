package com.dasha.usersystem.plan;

import com.dasha.usersystem.training.TrainingRepo;
import com.dasha.usersystem.plan.planService.TrainingFactoryService;
import com.dasha.usersystem.appUserInfo.AppUserInfoRepo;
import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PlanService {
    private final PlanRepo planRepo;
    private final TrainingRepo trainingRepo;
    private final AppUserRepo appUserRepo;
    private final AppUserInfoRepo appUserInfoRepo;
    private final TrainingFactoryService trainingFactoryService;

    @Transactional
    public void savePlan(String username, PlanRequest request){
        AppUser appUser = appUserRepo.findByUsername(username).orElseThrow(
                () ->new IllegalStateException("user not found"));
        Plan plan = new Plan(appUser, request);
        planRepo.save(plan);
        trainingFactoryService.createTrainings(plan);
        appUserInfoRepo.planExists(appUser, true);
    }

    @Transactional
    public Plan getPlan(String username){
        return planRepo.findPlanByAppUserUsername(username).orElseThrow(()->new IllegalStateException("plan not found"));
    }

    @Transactional
    public void deletePlan(String username) {
        AppUser appUser = appUserRepo.findByUsername(username).orElseThrow(
                () ->new IllegalStateException("user not found"));
        Plan plan = getPlan(username);
        trainingRepo.deleteAllByPlanId(plan.getId());
        planRepo.delete(plan);
        appUserInfoRepo.planExists(appUser, false);
    }

}
