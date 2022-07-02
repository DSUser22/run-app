package com.dasha.usersystem.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlanRepo extends JpaRepository<Plan, Long>{
    @Query("FROM Plan p WHERE p.appUser.id = ?1")
    Optional<Plan> findPlanByAppUserId(Long userId);
}
