package com.dasha.usersystem.appUserInfo;

import com.dasha.usersystem.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserInfoRepo extends JpaRepository<AppUserInfo, Long> {
    @Query("FROM AppUserInfo WHERE username = ?1")
    Optional<AppUserInfo> findAppUserInfoByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE AppUserInfo u SET " +
            "u.firstName = ?2, " +
            "u.secondName = ?3," +
            "u.city = ?4," +
            "u.age = ?5 WHERE u.appUser = ?1")
    void update(AppUser appUser, String firstName,
                           String secondName, String city, Integer age);


    /*@Transactional
    @Modifying
    @Query("UPDATE AppUserInfo u SET u.isDoesPlanExist = true WHERE u.username = ?1")
    void planExists(String username);*/

    /*@Transactional
    @Modifying
    @Query("UPDATE AppUserInfo u SET u.isDoesPlanExist = false WHERE u.appUser.username = ?1")
    void planDoesntExist(String username);*/

}