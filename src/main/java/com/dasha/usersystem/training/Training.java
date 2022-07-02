package com.dasha.usersystem.training;

import com.dasha.usersystem.training.type.running.Running;
import com.dasha.usersystem.plan.Plan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class Training implements Serializable {
    @Id
    @SequenceGenerator(
            name = "training_sequence",
            sequenceName = "training_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "training_sequence"
    )
    private Long id;
    private Integer trainingNumber;
    private Integer weekNumber;
    private LocalDate date;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    @JsonIgnore
    private Plan plan;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            nullable = false,
            name = "running_id",
            referencedColumnName = "id"
    )
    private Running running;
    private Boolean isDone = false;

    public Training(Integer trainingNumber,
                    Integer weekNumber,
                    LocalDate date,
                    Plan plan,
                    Running running) {
        this.trainingNumber = trainingNumber;
        this.weekNumber = weekNumber;
        this.date = date;
        this.running = running;
        this.plan = plan;
    }
}
