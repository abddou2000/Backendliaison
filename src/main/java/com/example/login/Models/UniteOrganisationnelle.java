package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "unite_organisationnelle")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UniteOrganisationnelle {

    @Id
    @Column(name = "id_unite")
    private String idUnite;

    @Column(name = "code_unite")
    private String codeUnite;

    @Column(name = "nom_unite")
    private String nomUnite;

    @Column(name = "type_unite")
    private String typeUnite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rattachement")
    @JsonBackReference
    private UniteOrganisationnelle uniteParent;

    @OneToMany(mappedBy = "uniteParent", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<UniteOrganisationnelle> sousUnites = new HashSet<>();

    @Column(name = "description_unite", columnDefinition = "TEXT")
    private String descriptionUnite;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_societe")
    private Societe societe;

    @OneToMany(mappedBy = "uniteOrganisationnelle", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<EmployeSimple> employes = new HashSet<>();

    public UniteOrganisationnelle() {}

    public UniteOrganisationnelle(String idUnite, String codeUnite, String nomUnite, String typeUnite) {
        this.idUnite = idUnite;
        this.codeUnite = codeUnite;
        this.nomUnite = nomUnite;
        this.typeUnite = typeUnite;
    }
}
