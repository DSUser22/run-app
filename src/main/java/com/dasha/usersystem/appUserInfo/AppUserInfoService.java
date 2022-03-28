package com.dasha.usersystem.appUserInfo;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserInfoService {
    private final AppUserInfoRepo appUserInfoRepo;
    private final AppUserRepo appUserRepo;

    public AppUserInfo getUserInfo(String email){
        Optional<AppUserInfo> optionalUserInfo = appUserInfoRepo.findAppUserInfoByAppUserUsername(email);
        return optionalUserInfo.orElseThrow(()-> new IllegalStateException("userInfo not found"));
    }

    public void modifyUserInfo(String username, AppUserInfoRequest request) {
        AppUser appUser = appUserRepo.findByUsername(username).orElseThrow(
                ()->new IllegalStateException("user not found"));
        appUserInfoRepo.update(appUser, request.getFirstName(),
                request.getSecondName(), request.getCity(), request.getAge());
    }
    public void deleteAppUserInfo(String username){
        appUserInfoRepo.deleteAppUserInfoByAppUserUsername(username);
    }



    public void addUserInfo(AppUserInfo appUserInfo) {
        appUserInfoRepo.save(appUserInfo);
    }
}
