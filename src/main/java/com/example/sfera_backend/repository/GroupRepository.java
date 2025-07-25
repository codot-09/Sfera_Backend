package com.example.sfera_backend.repository;

import com.example.sfera_backend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    boolean existsByName(String name);

    @Query("SELECT g FROM Group g WHERE g.admissionOpen = true")
    List<Group> findAllByAdmissionOpen(boolean status);
}
