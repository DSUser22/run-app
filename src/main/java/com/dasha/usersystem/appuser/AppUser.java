package com.dasha.usersystem.appuser;

import com.dasha.usersystem.appuser.role.Role;
import com.dasha.usersystem.plan.Plan;
import com.dasha.usersystem.refreshToken.RefreshToken;
import com.dasha.usersystem.security.auth.confToken.ConfirmationToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AppUser implements UserDetails, Serializable {

    @Id
    @SequenceGenerator(
            name = "app_user_seq",
            sequenceName = "app_user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_seq"
    )
    private Long id;
    private String username;
    private String password;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private Plan plan;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private RefreshToken refreshToken;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<ConfirmationToken> tokens;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> authorities;
    private Boolean locked = false;
    private Boolean enabled = true;

    public AppUser(String username,
                   String password,
                   Collection<Role> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    public AppUser(Long id, String username,
                   String password,
                   Collection<Role> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
