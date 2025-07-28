package com.example.login.Repositories;

import com.example.login.Models.ParametrageJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametrageJournalRepository extends JpaRepository<ParametrageJournal, String> {
}
