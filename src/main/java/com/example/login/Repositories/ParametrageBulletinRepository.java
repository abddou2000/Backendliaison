package com.example.login.Repositories;

import com.example.login.Models.ParametrageBulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametrageBulletinRepository extends JpaRepository<ParametrageBulletin, String> {
}
