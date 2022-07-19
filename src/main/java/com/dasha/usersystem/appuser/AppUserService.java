package com.dasha.usersystem.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    @Autowired private AppUserRepo repo;
    public List<AppUser> findAllAppUsers(){
        return repo.findAll();
    }
}
