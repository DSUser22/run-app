package com.dasha.usersystem.appUserInfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AppUserInfoRequest {
    private String firstName;
    private String secondName;
    private String city;
    private Integer age;
}
