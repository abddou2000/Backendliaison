package com.example.login.Services;

import com.example.login.Models.Configurateur;
import java.util.List;

public interface ConfigurateurService {
    List<Configurateur> listAll();
    Configurateur getById(Long id);
    Configurateur create(Configurateur configurateur);
    void delete(Long id);
}
