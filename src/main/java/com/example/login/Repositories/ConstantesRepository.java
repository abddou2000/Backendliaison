package com.example.login.Repositories;

import com.example.login.Models.Constantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConstantesRepository extends JpaRepository<Constantes, String> {

    List<Constantes> findByCodeConst(String codeConst);
    List<Constantes> findByNomConst(String nomConst);
    List<Constantes> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date start, Date end);
    List<Constantes> findByDateDebutAfter(Date date);
    List<Constantes> findByDateFinBefore(Date date);
}
