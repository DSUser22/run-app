package com.dasha.usersystem.appUserInfo;

import com.dasha.usersystem.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserInfo {
    @Id
    @SequenceGenerator(
            name = "user_info_sequence",
            sequenceName = "user_info_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_info_sequence"
    )
    private Long id;
    private String firstName;
    private String secondName;
    private String city;
    private Integer age;
    private boolean doesPlanExist;
    private String photo_link;
    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "username",
            referencedColumnName = "username"
    )
    private AppUser appUser;
    public AppUserInfo(AppUser appUser){
        this.firstName = null;
        this.secondName = null;
        this.city = null;
        this.age = null;
        this.doesPlanExist = false;
        this.appUser = appUser;
    }
    public AppUserInfo(AppUser user, AppUserInfoRequest request){
        this.appUser = user;
        this.firstName = request.getFirstName();
        this.secondName = request.getSecondName();
        this.city = request.getCity();
        this.age = request.getAge();
    }
}
