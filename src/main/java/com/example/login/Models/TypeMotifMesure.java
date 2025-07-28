package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "type_motif_mesure")
@Getter
@Setter
public class TypeMotifMesure {

    @Id
    @Column(name = "id_typemotifmesure")
    private String idTypeMotifMesure;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "typeMotifMesure")
    private List<MotifMesure> motifMesures;

    public TypeMotifMesure() {}

    public TypeMotifMesure(String idTypeMotifMesure, String code, String libelle) {
        this.idTypeMotifMesure = idTypeMotifMesure;
        this.code = code;
        this.libelle = libelle;
    }

    public TypeMotifMesure(String idTypeMotifMesure, String code, String libelle, String description) {
        this(idTypeMotifMesure, code, libelle);
        this.description = description;
    }

    public TypeMotifMesure(String idTypeMotifMesure, String code, String libelle, String description,
                           Date dateDebut, Date dateFin) {
        this(idTypeMotifMesure, code, libelle, description);
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
}