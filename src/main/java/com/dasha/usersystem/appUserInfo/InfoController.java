package com.dasha.usersystem.appUserInfo;

import com.dasha.usersystem.appuser.AppUserService;
import com.dasha.usersystem.security.jwt.JWTUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "my")
public class InfoController {

    private final AppUserInfoService infoService;
    //private final TrainingsService trainingsService;
    private final JWTUtility jwtUtility;
    private AppUserService appUserService;

    @GetMapping(path = "isplanexists")
    public boolean isPlanExist(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        return infoService.getUserInfo(username).isDoesPlanExist();
    }
    @GetMapping(path = "info")
    public AppUserInfo info(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        return infoService.getUserInfo(username);
    }
    @PutMapping(path = "modifyinfo")
    public void modifyPlan(@RequestHeader(name="Authorization") String token, @RequestBody AppUserInfoRequest request){
        String username = getUsername(token);
        infoService.modifyUserInfo(username, request);
    }

    //запрос информации о пользователе(AppUser_info)
    public void getUserInfo(){


    }

    //запрос информации о дате забега


    // вывод на главный экран информации о достигнутом


    // выод на главный экран тренировки на текущий день или отдых

    String getUsername(String token){
        return jwtUtility.getUsernameFromToken(token.substring(7));
    }
}
