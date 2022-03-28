package com.dasha.usersystem.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepo extends JpaRepository<Plan, Long>{
    /*@Query("FROM Plan WHERE username = ?1")
    Optional<Plan> findPlanByUsername(String username);*/

    Optional<Plan> findPlanByAppUserUsername(String username);

    /*@Query("DELETE FROM Plan WHERE username = ?1")
    void deleteByUsername(String username);*/

    void deleteByAppUserUsername(String username);
}
