package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "type_contrat")
@Getter
@Setter
public class TypeContrat {

    @Id
    @Column(name = "code_contrat")
    private String codeContrat;

    @Column(name = "nom_contrat")
    private String nomContrat;

    @Column(name = "periode_essai")
    private String periodeEssai; // ou Enum si valeurs fixes (ex: 1 mois, 3 mois)

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "conditions_specifiques", columnDefinition = "TEXT")
    private String conditionsSpecifiques;

    // Constructeurs
    public TypeContrat() {}

    public TypeContrat(String codeContrat, String nomContrat, String periodeEssai,
                       Date dateDebut, Date dateFin, String conditionsSpecifiques) {
        this.codeContrat = codeContrat;
        this.nomContrat = nomContrat;
        this.periodeEssai = periodeEssai;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.conditionsSpecifiques = conditionsSpecifiques;
    }

    @Override
    public String toString() {
        return "TypeContrat{" +
                "codeContrat='" + codeContrat + '\'' +
                ", nomContrat='" + nomContrat + '\'' +
                ", periodeEssai='" + periodeEssai + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
