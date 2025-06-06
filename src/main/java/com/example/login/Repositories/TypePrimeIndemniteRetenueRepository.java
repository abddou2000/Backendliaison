package com.example.login.Repositories;

import com.example.login.Models.TypePrimeIndemniteRetenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypePrimeIndemniteRetenueRepository extends JpaRepository<TypePrimeIndemniteRetenue, String> {

    /** 1. Recherche par type (Prime, Indemnité, Retenue) */
    List<TypePrimeIndemniteRetenue> findByType(String type);

    /** 2. Recherche par unité (Nombre, Montant fixe, Pourcentage) */
    List<TypePrimeIndemniteRetenue> findByUnite(String unite);

    /** 3. Filtrer par soumisCNSS */
    List<TypePrimeIndemniteRetenue> findBySoumisCNSS(Boolean soumis);

    /** 4. Filtrer par soumisAMO */
    List<TypePrimeIndemniteRetenue> findBySoumisAMO(Boolean soumis);

    /** 5. Filtrer par soumisCIMR */
    List<TypePrimeIndemniteRetenue> findBySoumisCIMR(Boolean soumis);

    /** 6. Filtrer par soumisIR */
    List<TypePrimeIndemniteRetenue> findBySoumisIR(Boolean soumis);
}
