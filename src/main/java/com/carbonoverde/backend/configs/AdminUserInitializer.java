package com.carbonoverde.backend.configs;

import com.carbonoverde.backend.entities.User;
import com.carbonoverde.backend.enums.UserRole;
import com.carbonoverde.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createManagerUser();
    }

    private void createManagerUser() {
        String managerUsername = "manager";
        
        if (userRepository.findByUsername(managerUsername).isEmpty()) {
            try {
                User managerUser = User.builder()
                        .name("System Manager")
                        .username(managerUsername)
                        .password(passwordEncoder.encode("Manager123!"))
                        .email("manager@carbonoverde.com")
                        .city("Joinville")
                        .role(UserRole.MANAGER)
                        .build();

                User savedUser = userRepository.save(managerUser);
                
                log.info("✅ Usuário MANAGER criado com sucesso!");
                log.info("👤 Username: {}", managerUsername);
                log.info("🔑 Password: Manager123!");
                log.info("🏷️ Role: {}", UserRole.MANAGER);
                log.info("📧 Email: manager@carbonoverde.com.br");
                log.info("🏙️ Cidade: Joinville");
                
            } catch (Exception e) {
                log.error("❌ Erro ao criar usuário MANAGER: {}", e.getMessage());
            }
        } else {
            log.info("✅ Usuário MANAGER já existe no sistema");
        }
    }
}
