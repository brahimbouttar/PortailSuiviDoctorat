package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;

import java.util.List;

public interface EncadrantService {
    //creation
    Encadrant createEncadrant(Encadrant u);
    //modification
    Encadrant updateEncadrant(Encadrant u, Long id);
    //lecture
    Encadrant findEncadrant(Long id);
    //list all
    List<Encadrant> findAllEncadrants();
    //Suppression
    void DeleteEncadrant(Long id);
}
