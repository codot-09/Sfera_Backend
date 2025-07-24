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
@Table(name = "leads")
public class Lead extends AuditableEntity {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Group group;

    private boolean confirmed;
}
