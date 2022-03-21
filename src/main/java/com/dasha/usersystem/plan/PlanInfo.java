package com.dasha.usersystem.plan;

import com.dasha.usersystem.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanInfo {
    @Id
    @SequenceGenerator(
            name = "plan_info_sequence",
            sequenceName = "plan_info_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plan_info_sequence"
    )
    private Long id;
    private LocalDate marathon_day;
    private Integer times_a_week;
    private Integer long_run;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "username",
            referencedColumnName = "username"
    )
    private AppUser appUser;
    public PlanInfo(AppUser appUser, PlanRequest request){
        this.appUser = appUser;
        this.long_run = request.getLong_run();
        this.marathon_day = LocalDate.parse(request.getMarathon_day());
        this.times_a_week = request.getTimes_a_week();
    }
}
