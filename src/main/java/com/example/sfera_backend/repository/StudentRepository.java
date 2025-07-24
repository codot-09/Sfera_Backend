package com.example.sfera_backend.repository;

import com.example.sfera_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByFullName(String fullName);
    boolean existsByFullNameAndIdNot(String fullName, UUID id);
}
