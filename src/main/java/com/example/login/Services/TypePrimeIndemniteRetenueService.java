package com.example.login.Services;

import com.example.login.Models.TypePrimeIndemniteRetenue;
import com.example.login.Repositories.TypePrimeIndemniteRetenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypePrimeIndemniteRetenueService {

    private final TypePrimeIndemniteRetenueRepository repo;

    @Autowired
    public TypePrimeIndemniteRetenueService(TypePrimeIndemniteRetenueRepository repo) {
        this.repo = repo;
    }

    public TypePrimeIndemniteRetenue create(TypePrimeIndemniteRetenue t) {
        return repo.save(t);
    }

    public TypePrimeIndemniteRetenue update(String id, TypePrimeIndemniteRetenue details) {
        Optional<TypePrimeIndemniteRetenue> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        TypePrimeIndemniteRetenue existing = opt.get();
        existing.setType(details.getType());
        existing.setUnite(details.getUnite());
        existing.setNombre(details.getNombre());
        existing.setMontantFixe(details.getMontantFixe());
        existing.setTauxPourcentage(details.getTauxPourcentage());
        existing.setSoumisCNSS(details.getSoumisCNSS());
        existing.setSoumisAMO(details.getSoumisAMO());
        existing.setSoumisCIMR(details.getSoumisCIMR());
        existing.setSoumisIR(details.getSoumisIR());
        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public TypePrimeIndemniteRetenue getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<TypePrimeIndemniteRetenue> listAll() {
        return repo.findAll();
    }

    public List<TypePrimeIndemniteRetenue> findByType(String type) {
        return repo.findByType(type);
    }

    public List<TypePrimeIndemniteRetenue> findByUnite(String unite) {
        return repo.findByUnite(unite);
    }

    public List<TypePrimeIndemniteRetenue> findBySoumisCNSS(Boolean soumis) {
        return repo.findBySoumisCNSS(soumis);
    }

    public List<TypePrimeIndemniteRetenue> findBySoumisAMO(Boolean soumis) {
        return repo.findBySoumisAMO(soumis);
    }

    public List<TypePrimeIndemniteRetenue> findBySoumisCIMR(Boolean soumis) {
        return repo.findBySoumisCIMR(soumis);
    }

    public List<TypePrimeIndemniteRetenue> findBySoumisIR(Boolean soumis) {
        return repo.findBySoumisIR(soumis);
    }
}
