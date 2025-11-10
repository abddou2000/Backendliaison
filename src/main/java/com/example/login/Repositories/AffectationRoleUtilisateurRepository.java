package com.example.login.Repositories;

import com.example.login.Models.AffectationRoleUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AffectationRoleUtilisateurRepository
        extends JpaRepository<AffectationRoleUtilisateur, AffectationRoleUtilisateur.Id> {

    List<AffectationRoleUtilisateur> findByUtilisateur_Id(Long userId);
    List<AffectationRoleUtilisateur> findByRole_Id(Long roleId);
    boolean existsByUtilisateur_IdAndRole_Id(Long userId, Long roleId);

    @Modifying
    @Transactional
    @Query("delete from AffectationRoleUtilisateur a where a.utilisateur.id = :userId and a.role.id = :roleId")
    void deleteByUserAndRole(Long userId, Long roleId);
}