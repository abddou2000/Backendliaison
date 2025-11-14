package com.example.login.Services;

import com.example.login.Models.EmployeSimple;
import com.example.login.Repositories.EmployeSimpleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service  // ⬅️ CRUCIAL : Cette annotation permet à Spring de détecter le bean !
@Transactional
public class EmployeSimpleServiceImpl implements EmployeSimpleService {

    @Autowired
    private EmployeSimpleRepository employeSimpleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeSimple> findAll(Pageable pageable) {
        return employeSimpleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findAll() {
        return employeSimpleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeSimple> findById(Long id) {
        return employeSimpleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeSimple> findByMatricule(String matricule) {
        return employeSimpleRepository.findByUtilisateur_Matricule(matricule);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeSimple> findByUtilisateurId(Long utilisateurId) {
        return employeSimpleRepository.findByUtilisateurId(utilisateurId);
    }

    @Override
    @Transactional
    public EmployeSimple save(EmployeSimple employe) {
        // 🔥 CRITIQUE : Si l'employé est nouveau (pas d'ID en base), utiliser persist
        if (employe.getId() != null && !employeSimpleRepository.existsById(employe.getId())) {
            // C'est une nouvelle entité avec @MapsId - il faut persist, pas merge
            System.out.println("🔥 Nouvelle entité détectée - utilisation de persist() pour ID: " + employe.getId());
            entityManager.persist(employe);
            entityManager.flush();
            return employe;
        } else if (employe.getId() != null && employeSimpleRepository.existsById(employe.getId())) {
            // C'est une mise à jour - on peut utiliser save (qui fait merge)
            System.out.println("♻️ Mise à jour entité existante - utilisation de save()");
            return employeSimpleRepository.save(employe);
        } else {
            // Cas où l'ID est null (ne devrait pas arriver avec @MapsId)
            throw new IllegalStateException("L'ID de l'employé ne peut pas être null");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        employeSimpleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return employeSimpleRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUtilisateurId(Long utilisateurId) {
        return employeSimpleRepository.existsByUtilisateurId(utilisateurId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByDepartement(String departement) {
        return employeSimpleRepository.findByDepartement(departement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByPosteOccupe(String posteOccupe) {
        return employeSimpleRepository.findByPosteOccupe(posteOccupe);
    }
}
