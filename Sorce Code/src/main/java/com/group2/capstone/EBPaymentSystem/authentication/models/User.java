package com.group2.capstone.EBPaymentSystem.authentication.models;

import com.group2.capstone.EBPaymentSystem.billing.models.UserProfile;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Column(unique = true)
    private String username;
    private String password;

    @OneToOne
    private UserProfile userProfile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_junction", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities;

    public User() {
        super();
        authorities = new HashSet<>();
    }

    public User(long userId, String username, String password, Set<Role> authorities) {
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public User(long userId, String username, String password, Set<Role> authorities, UserProfile userProfile) {
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.userProfile = userProfile;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * Currently, the following variables and associated methods are not used but are part of UserDetails class
     *
     */
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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username='" + username + '\'' + ", password='" + password + '\'' + ", userProfile=" + userProfile + ", authorities=" + authorities + '}';
    }
}
