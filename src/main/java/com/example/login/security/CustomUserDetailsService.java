package com.example.login.security;

import com.example.login.repositories.EmployeSimpleRepository;
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
                .map(emp -> {
                    String pwd = emp.getMotDePasse();
                    if (pwd != null && pwd.startsWith("$2")) {
                        pwd = "{bcrypt}" + pwd;
                    } else if (pwd != null && !pwd.startsWith("{")) {
                        pwd = "{noop}" + pwd;
                    }
                    String role = emp.getRole() != null
                            ? emp.getRole().getNomRole().toUpperCase()
                            : "USER";
                    return User.builder()
                            .username(emp.getEmailPro())
                            .password(pwd)
                            .roles(role)
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + email));
    }
}
