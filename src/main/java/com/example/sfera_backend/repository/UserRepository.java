package com.example.sfera_backend.repository;

import com.example.sfera_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByChatId(Long chatId);
    Optional<User> findByPhone(String phone);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findByChatId(Long chatId);
}
