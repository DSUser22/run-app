package com.dasha.usersystem.plan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlanRequest {
    private String marathon_day;
    private Integer times_a_week;
    private Integer long_run;
}
