package com.dasha.usersystem.training.type.running;
import com.dasha.usersystem.training.TrainingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@DiscriminatorValue("SPEED_RUN")
public class SpeedRunning extends Running implements Serializable {
    private int level;
    private int rep;
    private int interval;
    private int distance;
    private TrainingType type = TrainingType.SPEED;
    public SpeedRunning(int level){
        Integer[] speedTrain = getRandomSpeedTrain(level);
        this.level = level;
        rep = speedTrain[0];
        interval = speedTrain[1];
        distance = (int)Math.round(((double)rep * interval)/1000);
    }

    private static final List<Integer[]> level11 = Arrays.asList(
            new Integer[]{6, 100},
            new Integer[]{4, 150},
            new Integer[]{3, 200});
    private static final List<Integer[]> level22 = Arrays.asList(
            new Integer[]{5, 300},
            new Integer[]{3, 500},
            new Integer[]{4, 400});
    private static final List<Integer[]> level33 = Arrays.asList(
            new Integer[]{5, 600},
            new Integer[]{6, 500},
            new Integer[]{4, 750});
    private Integer[] getRandomSpeedTrain(int level){
        int random = new Random().nextInt(3);
        switch (level){
            case 1:
                return level11.get(random);
            case 2:
                return level22.get(random);
            default:
                return level33.get(random);
        }
    }
}
