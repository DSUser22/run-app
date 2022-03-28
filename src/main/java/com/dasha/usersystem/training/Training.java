package com.dasha.usersystem.training;

import com.dasha.usersystem.training.type.running.Running;
import com.dasha.usersystem.plan.Plan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            name = "plan_id",
            referencedColumnName = "id"
    )
    private Plan plan;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinColumn(
            nullable = false,
            name = "running_id",
            referencedColumnName = "id"
    )
    @Enumerated(EnumType.STRING)
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
