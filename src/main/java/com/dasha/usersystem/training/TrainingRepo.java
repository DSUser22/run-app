package com.dasha.usersystem.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepo extends JpaRepository<Training, Long> {
    @Query("FROM Training t WHERE t.plan.appUser.id = ?1 AND t.date = ?2")
    Optional<Training> findTrainingByAppUserIdAndDate(Long userId, LocalDate date);

    @Query("FROM Training t WHERE t.plan.appUser.id = ?1 AND t.trainingNumber = ?2")
    Optional<Training> findTrainingByAppUserIdAndNumber(Long userId, Integer number);

    @Query("SELECT t FROM Training t WHERE t.plan.appUser.id = ?1")
    List<Training> findAllByAppUserId(Long userId);

    @Query("UPDATE Training t SET t.isDone = ?3 WHERE t.plan.appUser.id = ?1 AND t.trainingNumber = ?2")
    @Transactional
    @Modifying
    int updateIsDone(Long userId, Integer number, boolean isDone);
}
