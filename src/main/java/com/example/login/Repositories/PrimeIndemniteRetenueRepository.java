package com.example.login.Repositories;

import com.example.login.Models.PrimeIndemniteRetenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimeIndemniteRetenueRepository extends JpaRepository<PrimeIndemniteRetenue, String> {
    List<PrimeIndemniteRetenue> findByEmploye_IdEmploye(String idEmploye);
    List<PrimeIndemniteRetenue> findByPeriodePaie_IdPeriode(String idPeriode);
    List<PrimeIndemniteRetenue> findByTypePrime_IdTypePrime(String idTypePrime);
}
