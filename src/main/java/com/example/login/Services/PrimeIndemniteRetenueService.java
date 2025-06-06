package com.example.login.Services;

import com.example.login.Models.PrimeIndemniteRetenue;
import com.example.login.Repositories.PrimeIndemniteRetenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrimeIndemniteRetenueService {

    @Autowired
    private PrimeIndemniteRetenueRepository repository;

    public PrimeIndemniteRetenue create(PrimeIndemniteRetenue prime) {
        return repository.save(prime);
    }

    public List<PrimeIndemniteRetenue> getAll() {
        return repository.findAll();
    }

    public Optional<PrimeIndemniteRetenue> getById(String id) {
        return repository.findById(id);
    }

    public List<PrimeIndemniteRetenue> getByEmploye(String idEmploye) {
        return repository.findByEmploye_IdEmploye(idEmploye);
    }

    public List<PrimeIndemniteRetenue> getByPeriode(String idPeriode) {
        return repository.findByPeriodePaie_IdPeriode(idPeriode);
    }

    public List<PrimeIndemniteRetenue> getByType(String idTypePrime) {
        return repository.findByTypePrime_IdTypePrime(idTypePrime);
    }

    public PrimeIndemniteRetenue update(String id, PrimeIndemniteRetenue updated) {
        updated.setIdPrime(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
