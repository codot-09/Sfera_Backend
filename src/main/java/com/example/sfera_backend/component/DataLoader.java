package com.example.sfera_backend.component;

import com.example.sfera_backend.entity.User;
import com.example.sfera_backend.entity.enums.UserRole;
import com.example.sfera_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if(ddl.equals("create") || ddl.equals("create-drop")){
            User superAdmin = User.builder()
                    .chatId(7193645528L)
                    .phone("998916368424")
                    .fullName("Admin admin")
                    .role(UserRole.ADMIN)
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .build();

            userRepository.save(superAdmin);
        }
    }
}
