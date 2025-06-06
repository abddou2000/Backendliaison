package com.example.login.Repositories;

import com.example.login.Models.Constantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstantesRepository extends JpaRepository<Constantes, String> {
    List<Constantes> findByCodeConst(String codeConst);
    List<Constantes> findByConfigurateur_IdConfigurateur(String idConfigurateur);
}
