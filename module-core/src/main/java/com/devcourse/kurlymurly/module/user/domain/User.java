package com.devcourse.kurlymurly.module.user.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    public enum Role {ROLE_USER, ROLE_ADMIN}

    public enum UserStatus {CANCEL, NORMAL}

    public enum Tier {
        THE_PURPLE,
        PURPLE,
        LAVENDER,
        WHITE,
        FRIENDS;
    }

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Tier tier;

    @Column(nullable = false, length = 50)
    private String email;

    @Embedded
    private UserInfo info;

    @Column(length = 50)
    private String payPassword;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus status;

    protected User() {
    }

    public User(String name, String loginId, String password, String email, UserInfo info, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.tier = Tier.THE_PURPLE;
        this.email = email;
        this.info = info;
        this.payPassword = null;
        this.phoneNumber = phoneNumber;
        this.role = Role.ROLE_USER;
        this.status = UserStatus.NORMAL;
    }

    public void update(String name, String password, String email, String sex, LocalDate birth, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        info.update(birth, sex);
    }

    public void updatePayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public boolean validatePayPassword(String payPassword, PasswordEncoder encoder) {
        return encoder.matches(payPassword, this.payPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.name()));

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    public String getName() {
        return this.name;
    }

    public Tier getTier() {
        return this.tier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Role getRole() {
        return role;
    }

    public boolean isEqualPassword(String password) {
        return this.password.equals(password);
    }

    public String getMaskedUserName() {
        return this.name.replaceAll("(?<=.{1})", "*");
    }
}

