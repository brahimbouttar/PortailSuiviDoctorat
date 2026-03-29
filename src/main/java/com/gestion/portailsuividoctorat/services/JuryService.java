package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Jury;
import com.gestion.portailsuividoctorat.entites.Utilisateur;

import java.util.List;

public interface JuryService {
    // création
    Jury createJury(Jury j);
    // modification
    Jury updateJury(Jury j, Long id);
    // lecture
    Jury findJury(Long id);
    // liste de tous
    List<Jury> findAllJuries();
    // suppression
    void deleteJury(Long id);
}