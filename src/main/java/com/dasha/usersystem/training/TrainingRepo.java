package com.dasha.usersystem.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepo extends JpaRepository<Training, Long> {

    Optional<Training> findTrainingByPlanIdAndTrainingNumber(Long id, Integer trainingNumber);

    void deleteAllByPlanId(Long id);

    List<Training> findAllByPlanId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Training u SET u.isDone = true WHERE u.plan.id = ?1 AND u.id = ?2")
    void isDoneTraining(Long PlanId, Long trainingId);

}
