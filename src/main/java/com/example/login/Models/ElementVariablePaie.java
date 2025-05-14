package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "element_variable_paie")
@Getter
@Setter
public class ElementVariablePaie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_element_variable")
    private Long idElementVariable;
    
    @ManyToOne
    @JoinColumn(name = "id_periode")
    private PeriodePaie periodePaie;
    
    @ManyToOne
    @JoinColumn(name = "id_employe")
    private EmployeSimple employe;
    
    @ManyToOne
    @JoinColumn(name = "id_rubrique")
    private RubriquePaie rubriquePaie;
    
    @Column(name = "valeur")
    private BigDecimal valeur;
    
    @Column(name = "commentaire")
    private String commentaire;
    
    // Default constructor
    public ElementVariablePaie() {
    }
    
    // Constructor with required fields
    public ElementVariablePaie(PeriodePaie periodePaie, EmployeSimple employe, RubriquePaie rubriquePaie, BigDecimal valeur) {
        this.periodePaie = periodePaie;
        this.employe = employe;
        this.rubriquePaie = rubriquePaie;
        this.valeur = valeur;
    }
    
    // Constructor with all fields except id
    public ElementVariablePaie(PeriodePaie periodePaie, EmployeSimple employe, RubriquePaie rubriquePaie, 
                             BigDecimal valeur, String commentaire) {
        this.periodePaie = periodePaie;
        this.employe = employe;
        this.rubriquePaie = rubriquePaie;
        this.valeur = valeur;
        this.commentaire = commentaire;
    }
    
    @Override
    public String toString() {
        return "ElementVariablePaie{" +
                "idElementVariable=" + idElementVariable +
                ", periodePaie=" + (periodePaie != null ? periodePaie.getIdPeriode() : null) +
                ", employe=" + (employe != null ? employe.getIdEmploye() : null) +
                ", rubriquePaie=" + (rubriquePaie != null ? rubriquePaie.getIdRubrique() : null) +
                ", valeur=" + valeur +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElementVariablePaie)) return false;
        ElementVariablePaie that = (ElementVariablePaie) o;
        return idElementVariable != null && idElementVariable.equals(that.idElementVariable);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}