package com.dasha.usersystem.plan;

import com.dasha.usersystem.appUserInfo.AppUserInfoRepo;
import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlanService {
    private final PlanRepo planRepo;
    private final AppUserRepo appUserRepo;
    private final AppUserInfoRepo appUserInfoRepo;

    public void savePlanInfo(String username, PlanRequest request){
        AppUser appUser = appUserRepo.findByUsername(username).orElseThrow(
                () ->new IllegalStateException("user not found"));
        PlanInfo planInfo = new PlanInfo(appUser, request);
        planRepo.save(planInfo);
        // TODO:: в PlanUserInfo doesPlanExist
    }
    public PlanInfo getPlanInfo(String username){
        return planRepo.getPlanInfo(username).orElseThrow(()->new IllegalStateException("smth went wrong"));
    }

    // TODO:: deletePlan

    /*public void deletePlanInfo(String username) {
        planRepo.deleteByUsername(username);
        appUserInfoRepo.planDoesntExist(username);

    }*/
}
