package com.dasha.usersystem.security.auth.confToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime date);

    @Transactional
    @Modifying
    @Query("DELETE FROM ConfirmationToken c WHERE c.appUser.id = ?1")
    void deleteAllByAppUserId(Long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM confirmation_token WHERE user_id = ?1 order by id desc LIMIT 1")
    ConfirmationToken findLastConfTokenByAppUserId(Long userId);

    /*@Query("SELECT ConfirmationToken FROM ConfirmationToken c WHERE c.appUser.id = ?1 order by id desc  LIMIT 1")
    LocalDateTime findLastByAppUserId2(Long userId);*/
}
