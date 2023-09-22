package com.devcourse.kurlymurly.module.user.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import com.devcourse.kurlymurly.module.user.domain.strategy.Reward;
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
import java.util.Objects;

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

    private String payPassword;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer reward;

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
        this.payPassword = null;
        this.reward = 0;
        this.info = info;
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

    public boolean validatePassword(String password, PasswordEncoder encoder) {
        return encoder.matches(password, this.password);
    }

    public int saveReward(Reward reward, int totalPrice) {
        int totalReward = reward.saveReward(totalPrice);

        return this.reward += totalReward;
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

    public String getMaskedUserName() {
        return this.name.replaceAll("(?<=.{1})", "*");
    }

    public boolean isNotAuthor(Long id) {
        return !Objects.equals(this.getId(), id);
    }
}

