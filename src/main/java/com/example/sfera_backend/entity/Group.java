package com.example.sfera_backend.entity;

import com.example.sfera_backend.entity.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "groups")
public class Group extends AuditableEntity {
    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(nullable = false)
    private String imageUrl;

    private int maxStudentCount;

    private int currentStudentCount;

    @Column(nullable = false)
    private LocalDate openDate;

    private boolean admissionOpen = true;
}
