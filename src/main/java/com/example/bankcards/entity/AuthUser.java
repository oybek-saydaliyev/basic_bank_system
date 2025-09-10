package com.example.bankcards.entity;

import com.example.bankcards.base.BaseEntity;
import com.example.bankcards.entity.enums.AuthRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class AuthUser extends BaseEntity implements UserDetails {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean isActive = Boolean.TRUE;
    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private AuthRole role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> cards;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public AuthUser(String username, String password, AuthRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deleted;
    }
}
