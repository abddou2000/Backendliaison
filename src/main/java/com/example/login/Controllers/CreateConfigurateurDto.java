package com.example.login.Controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO “plat” pour créer un Configurateur, sans référence à EmployeSimple.
 */
@Getter
@Setter
public class CreateConfigurateurDto {

    @NotBlank(message = "Le nom est requis")
    private String nom;

    @NotBlank(message = "Le prénom est requis")
    private String prenom;

    @NotBlank(message = "L'email est requis")
    @Email(message = "Format d'email invalide")
    private String emailPro;

    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;

    @NotBlank(message = "Le téléphone est requis")
    private String telephone;

    @NotBlank(message = "L'adresse est requise")
    private String adresse;

    @NotBlank(message = "Le CIN est requis")
    private String cin;

    @NotBlank(message = "Le nom d'utilisateur est requis")
    private String nomUtilisateur;
}
