package com.dasha.usersystem.plan;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Trainings {
    @Id
    @SequenceGenerator(
            name = "trainings_sequence",
            sequenceName = "trainings_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trainings_sequence"
    )
    private int id;
    private int train_num;
    private int week_num;
    private int level;
    private int type;
    private double distance;
    private int gym; // ? boolean gym;
    private int reps;
    private int reps_dist;
    private boolean isDone;
    private Date time;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "plan_info_id",
            referencedColumnName = "username"
    )
    private PlanInfo plan_info;
}
