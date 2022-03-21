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
        Optional<AppUserInfo> optionalUserInfo = appUserInfoRepo.findAppUserInfoByUsername(email);
        return optionalUserInfo.orElseThrow(()-> new IllegalStateException("userInfo not found"));
    }

    public void modifyUserInfo(String username, AppUserInfoRequest request) {
        /*appUserInfoRepo.updateAppUserInfo(username, request.getFirstName(),
                request.getSecondName(), request.getCity(), request.getAge());*/
        AppUser appUser = appUserRepo.findByUsername(username).orElseThrow(
                ()->new IllegalStateException("user not found"));
        appUserInfoRepo.update(appUser, request.getFirstName(),
                request.getSecondName(), request.getCity(), request.getAge());
    }

    public void addUserInfo(AppUserInfo appUserInfo) {
        appUserInfoRepo.save(appUserInfo);
    }
}
