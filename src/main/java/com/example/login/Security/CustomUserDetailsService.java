package com.example.login.Security;

import com.example.login.Repositories.EmployeSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeSimpleRepository employeSimpleRepository;

    @Autowired
    public CustomUserDetailsService(EmployeSimpleRepository employeSimpleRepository) {
        this.employeSimpleRepository = employeSimpleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeSimpleRepository.findByEmailProWithRole(email)
                .or(() -> employeSimpleRepository.findByEmailPersoWithRole(email))
                .map(employeSimple -> {
                    String password = employeSimple.getMotDePasse();

                    // Nettoyage et gestion du mot de passe encodé
                    if (password != null) {
                        if (password.startsWith("{noop}") || password.startsWith("{bcrypt}")) {
                            // OK
                        } else if (password.startsWith("$2")) {
                            password = "{bcrypt}" + password;
                        } else {
                            password = "{noop}" + password;
                        }
                    }

                    // Important : ajouter le préfixe ROLE_ attendu par Spring Security
                    String roleName = employeSimple.getRole() != null
                            ? employeSimple.getRole().getNomRole().toUpperCase()
                            : "USER";

                    return User.builder()
                            .username(employeSimple.getEmailPro())
                            .password(password)
                            .roles(roleName) // Spring ajoutera "ROLE_" automatiquement ici
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
