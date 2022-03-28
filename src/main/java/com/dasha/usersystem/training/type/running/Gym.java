package com.dasha.usersystem.training.type.running;

import com.dasha.usersystem.training.TrainingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@DiscriminatorValue("GYM_RUN")
public class Gym extends Running implements Serializable {
    private TrainingType type = TrainingType.GYM;
}
