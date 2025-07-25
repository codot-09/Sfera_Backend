package com.example.sfera_backend.repository;

import com.example.sfera_backend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    boolean existsByPhone(String phone);

    List<Teacher> findAllByActive(boolean active);
}
