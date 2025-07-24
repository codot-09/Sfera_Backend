package com.example.sfera_backend.entity;

import com.example.sfera_backend.entity.audit.AuditableEntity;
import com.example.sfera_backend.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends AuditableEntity {
    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false,unique = true)
    private String chatId;

    @Column(nullable = false,unique = true)
    private String phone;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private boolean active;
}
