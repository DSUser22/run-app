package com.dasha.usersystem.appUserInfo;

import com.dasha.usersystem.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserInfo implements Serializable {
    @Id
    @SequenceGenerator(
            name = "app_user_info_sequence",
            sequenceName = "app_user_info_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_info_sequence"
    )
    private Long id;
    private String firstName;
    private String secondName;
    private String city;
    private Integer age;
    private boolean planExist;
    private String photoLink;
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
        this.planExist = false;
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
