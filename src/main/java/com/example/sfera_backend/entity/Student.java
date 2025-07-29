package com.example.sfera_backend.entity;

import com.example.sfera_backend.entity.audit.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import javax.swing.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Student extends AuditableEntity {

    private String fullName;

    private String description;

    private String job;

    private String company;

    private String phoneNumber;

    private String fileUrl;
}
