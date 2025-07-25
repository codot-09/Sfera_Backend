package com.example.sfera_backend.repository;

import com.example.sfera_backend.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface LeadRepository extends JpaRepository<Lead, UUID>, JpaSpecificationExecutor<Lead> {
    boolean existsByPhone(String phone);
}
