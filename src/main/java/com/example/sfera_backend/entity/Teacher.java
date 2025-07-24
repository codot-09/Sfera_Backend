package com.example.sfera_backend.entity;

import com.example.sfera_backend.entity.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "teachers")
public class Teacher extends AuditableEntity {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String phone;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String biography;
}
