package com.carbonoverde.backend.configs.security;

import com.carbonoverde.backend.entities.User;
import com.carbonoverde.backend.repositories.CompanyRepository;
import com.carbonoverde.backend.repositories.CompensationRepository;
import com.carbonoverde.backend.repositories.MonthlyEmissionRepository;
import com.carbonoverde.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {

    private final UserRepository userRepository;
    private final SecurityRules securityRules;
    private final CompanyRepository companyRepository;
    private final MonthlyEmissionRepository monthlyEmissionRepository;

    public boolean isOwnProfile(Authentication authentication, Long userId) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId().equals(userId);
    }

    public boolean canAccessCompany(Authentication authentication, Long companyId) {
        // MANAGER tem acesso a tudo
        if (securityRules.isManager(authentication)) {
            return true;
        }

        // ADMIN só acessa empresas da mesma cidade
        if (securityRules.isAdmin(authentication)) {
            try {
                String userCity = securityRules.getCurrentUserCity(authentication);
                return companyRepository.existsByIdAndCity(companyId, userCity);
            } catch (Exception e) {
                log.error("Erro ao verificar acesso à empresa: {}", e.getMessage());
                return false;
            }
        }

        // USER não tem acesso a empresas individuais
        return false;
    }

    // Método auxiliar para criação de empresa
    public boolean canCreateCompanyInCity(Authentication authentication, String city) {
        // MANAGER pode criar em qualquer cidade
        if (securityRules.isManager(authentication)) {
            return true;
        }

        // ADMIN só pode criar na mesma cidade
        if (securityRules.isAdmin(authentication)) {
            try {
                String userCity = securityRules.getCurrentUserCity(authentication);
                return userCity.equalsIgnoreCase(city);
            } catch (Exception e) {
                log.error("Erro ao verificar criação de empresa: {}", e.getMessage());
                return false;
            }
        }

        // USER não pode criar empresas
        return false;
    }

    public boolean canAccessMonthlyEmission(Authentication authentication, Long emissionId) {
        // MANAGER tem acesso a tudo
        if (securityRules.isManager(authentication)) {
            return true;
        }

        // ADMIN só acessa emissões da mesma cidade
        if (securityRules.isAdmin(authentication)) {
            try {
                String userCity = securityRules.getCurrentUserCity(authentication);

                // Verificar se a emissão pertence a uma empresa da mesma cidade
                return monthlyEmissionRepository.existsByIdAndCompanyCity(emissionId, userCity);
            } catch (Exception e) {
                log.error("Erro ao verificar acesso à emissão mensal: {}", e.getMessage());
                return false;
            }
        }

        // USER não tem acesso a emissões individuais
        return false;
    }
}
