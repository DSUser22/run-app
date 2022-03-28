package com.dasha.usersystem.training.type.running;

import com.dasha.usersystem.training.TrainingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@DiscriminatorValue("ORDINARY_RUN")
public class OrdinaryRunning extends Running implements Serializable {
    private int distance;
    private TrainingType type = TrainingType.ORDINARY;
    public OrdinaryRunning(int distance){
        this.distance = distance;
    }
}
