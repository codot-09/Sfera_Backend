package com.example.sfera_backend.entity;

import com.example.sfera_backend.entity.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courses")
public class Course extends AuditableEntity {
    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private int duration;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    private String videoUrl;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
