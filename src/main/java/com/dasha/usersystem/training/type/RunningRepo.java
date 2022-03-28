package com.dasha.usersystem.training.type;

import com.dasha.usersystem.training.type.running.Running;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningRepo extends JpaRepository<Running, Long> {
}
