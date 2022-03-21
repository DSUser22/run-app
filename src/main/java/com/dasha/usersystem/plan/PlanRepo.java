package com.dasha.usersystem.plan;

import com.dasha.usersystem.appUserInfo.AppUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepo extends JpaRepository<PlanInfo, Long>{
    @Query("FROM PlanInfo WHERE username = ?1")
    Optional<PlanInfo> getPlanInfo(String username);

    @Query("DELETE FROM PlanInfo WHERE username = ?1")
    void deleteByUsername(String username);
}
