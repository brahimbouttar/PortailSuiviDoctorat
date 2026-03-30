package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;

import java.util.List;

public interface DoctorantService {
    //creation
    Utilisateur createDoctorant(Doctorant u);
    //modification
    Utilisateur updateDoctorant(Doctorant u, Long id);
    //lecture
    Utilisateur findDoctorant(Long id);
    //list all
    List<Doctorant> findAllDoctorants();
    //Suppression
    void DeleteDoctorant(Long id);
}
