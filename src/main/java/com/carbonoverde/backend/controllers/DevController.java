package com.carbonoverde.backend.controllers;

import com.carbonoverde.backend.entities.User;
import com.carbonoverde.backend.enums.UserRole;
import com.carbonoverde.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DevController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/dev/seed-admin")
    public ResponseEntity<?> seedAdmin() {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(UserRole.MANAGER);
            
            user.setName("Administrador do Sistema");
            user.setEmail("admin@carbonoverde.com");
            user.setCity("Sistema");
            
            userRepository.save(user);
            return ResponseEntity.ok("Usuário admin criado!");
        }
        return ResponseEntity.badRequest().body("Já existe usuário cadastrado");
    }
}
