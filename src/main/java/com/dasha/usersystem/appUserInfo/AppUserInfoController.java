package com.dasha.usersystem.appUserInfo;

import com.dasha.usersystem.appuser.AppUserService;
import com.dasha.usersystem.security.jwt.JWTUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/my/info")
@CrossOrigin("*")
public class AppUserInfoController {

    private final AppUserInfoService appUserInfoService;
    private final JWTUtility jwtUtility;

    @GetMapping(path = "get")
    public AppUserInfo info(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);
        return appUserInfoService.getUserInfo(username);
    }
    @GetMapping(path = "planexists")
    public boolean doesPlanExist(@RequestHeader(name="Authorization") String token){
        return info(token).isPlanExist();
    }

    @PutMapping(path = "put")
    public void putPlan(@RequestHeader(name="Authorization") String token,
                        @Valid @RequestBody AppUserInfoRequest request){
        String username = getUsername(token);
        appUserInfoService.modifyUserInfo(username, request);
    }

    String getUsername(String token){
        return jwtUtility.getUsernameFromToken(token.substring(7));
    }
}
