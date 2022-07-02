package com.dasha.usersystem.plan;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.training.Training;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

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
            name = "plan_seq",
            sequenceName = "plan_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plan_seq"
    )
    private Long id;
    private LocalDate marathonDate;
    private Integer timesAWeek;
    private Integer longRun;

    private LocalDate firstTrainingDay;
    private int countOfWeeks;
    private int countOfTrainings;
    @JsonIgnore
    @OneToOne(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private AppUser appUser;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plan")
    private List<Training> trainings = null;

    public Plan(AppUser appUser, PlanRequest request){
        this.appUser = appUser;
        this.longRun = request.getLongRun();
        this.marathonDate = LocalDate.parse(request.getMarathonDate());
        this.timesAWeek = request.getTimesAWeek();

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
