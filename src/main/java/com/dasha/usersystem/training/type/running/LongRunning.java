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
@NoArgsConstructor
@ToString
@DiscriminatorValue("LONG_RUN")
public class LongRunning extends Running implements Serializable {
    private int distance;
    private TrainingType type = TrainingType.LONG;

    public LongRunning(int distance) {
        this.distance = distance;
    }
}
