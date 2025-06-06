package com.example.login.Repositories;

import com.example.login.Models.DeclarationSociale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeclarationSocialeRepository extends JpaRepository<DeclarationSociale, String> {
    List<DeclarationSociale> findByPeriodePaie_IdPeriode(String idPeriode);
    List<DeclarationSociale> findByTypeDeclaration(String typeDeclaration);
}
