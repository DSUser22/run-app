package com.dasha.usersystem.plan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlanRequest {
    private String marathonDate;
    @Min(3)
    @Max(5)
    private Integer timesAWeek;
    private Integer longRun;
}
