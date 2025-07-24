package com.example.sfera_backend.entity;

import com.example.sfera_backend.entity.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends AuditableEntity implements UserDetails {
    @Column
    private String fullName = "Adminstrator";

    @Column(nullable = false,unique = true)
    private Long chatId;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private String passwordHash;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private boolean verified;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return phone;
    }
}
