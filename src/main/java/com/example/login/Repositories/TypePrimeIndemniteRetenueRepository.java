// src/main/java/com/example/login/Repositories/TypePrimeIndemniteRetenueRepository.java
package com.example.login.Repositories;

import com.example.login.Models.TypePrimeIndemniteRetenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypePrimeIndemniteRetenueRepository extends JpaRepository<TypePrimeIndemniteRetenue, String> {

    List<TypePrimeIndemniteRetenue> findByType(String type);
    List<TypePrimeIndemniteRetenue> findByUnite(String unite);
    List<TypePrimeIndemniteRetenue> findBySoumisCNSS(Boolean soumis);
    List<TypePrimeIndemniteRetenue> findBySoumisAMO(Boolean soumis);
    List<TypePrimeIndemniteRetenue> findBySoumisCIMR(Boolean soumis);
    List<TypePrimeIndemniteRetenue> findBySoumisIR(Boolean soumis);
}

