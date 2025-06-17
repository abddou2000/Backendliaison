package com.example.login.security;

import com.example.login.models.Rh;
import com.example.login.repositories.EmployeSimpleRepository;
import com.example.login.repositories.RhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeSimpleRepository employeSimpleRepository;
    private final RhRepository rhRepository;

    @Autowired
    public CustomUserDetailsService(EmployeSimpleRepository employeSimpleRepository,
                                    RhRepository rhRepository) {
        this.employeSimpleRepository = employeSimpleRepository;
        this.rhRepository = rhRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1) Recherche dans EmployeSimple
        var empOpt = employeSimpleRepository.findByEmailProWithRole(email)
                .or(() -> employeSimpleRepository.findByEmailPersoWithRole(email));
        if (empOpt.isPresent()) {
            var emp = empOpt.get();
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
        }

        // 2) Recherche dans Rh
        var rhOpt = rhRepository.findByEmail(email);
        if (rhOpt.isPresent()) {
            Rh rh = rhOpt.get();
            String pwd = rh.getMotDePasse();
            if (pwd != null && pwd.startsWith("$2")) {
                pwd = "{bcrypt}" + pwd;
            } else if (pwd != null && !pwd.startsWith("{")) {
                pwd = "{noop}" + pwd;
            }
            return User.builder()
                    .username(rh.getEmail())
                    .password(pwd)
                    .roles("RH")
                    .build();
        }

        throw new UsernameNotFoundException("Utilisateur introuvable: " + email);
    }
}
