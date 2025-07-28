package com.example.login.Repositories;

import com.example.login.Models.RubriqueBulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubriqueBulletinRepository extends JpaRepository<RubriqueBulletin, String> {
}
