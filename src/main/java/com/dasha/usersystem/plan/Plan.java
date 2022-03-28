package com.dasha.usersystem.plan;

import com.dasha.usersystem.appuser.AppUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.ChronoUnit.WEEKS;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Plan implements Serializable {
    @Id
    @SequenceGenerator(
            name = "plan_sequence",
            sequenceName = "plan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plan_sequence"
    )
    private Long id;
    private LocalDate marathonDate;
    private Integer timesAWeek;
    private Integer longRun;

    private LocalDate firstTrainingDay;
    private int countOfWeeks;
    private int countOfTrainings;
    @OneToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE,
                           CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(
            nullable = false,
            name = "username",
            referencedColumnName = "username"
    )
    private AppUser appUser;

    public Plan(AppUser appUser, PlanRequest request){
        this.appUser = appUser;
        this.longRun = request.getLong_run();
        this.marathonDate = LocalDate.parse(request.getMarathon_day());
        this.timesAWeek = request.getTimes_a_week();

        firstTrainingDay = getFirstTrainDate();
        countOfWeeks = countWeeks(firstTrainingDay, marathonDate);
        countOfTrainings = (countOfWeeks-1) * timesAWeek + 3;
    }

    private static LocalDate getFirstTrainDate(){
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
    }
    public static Integer countWeeks(LocalDate firstTrainDate, LocalDate marathonDay){
        return (int) WEEKS.between(firstTrainDate, marathonDay) + 1;
    }
}
