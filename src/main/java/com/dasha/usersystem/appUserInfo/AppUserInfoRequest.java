package com.dasha.usersystem.appUserInfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AppUserInfoRequest {
    private String firstName;
    private String secondName;
    private String city;
    @Min(17)
    private Integer age;
}
