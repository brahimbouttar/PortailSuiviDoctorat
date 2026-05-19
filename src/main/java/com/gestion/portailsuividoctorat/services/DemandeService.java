package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Demande;
import java.util.List;

public interface DemandeService {
    Demande createDemande(Demande demande);
    Demande updateDemande(Long id, Demande demande);
    void deleteDemande(Long id);
    Demande getDemandeById(Long id);
    List<Demande> getAllDemandes();
    Demande createDemande(Demande demande, Long doctorantId);
    List<Demande> getDemandesByDoctorant(Long doctorantId);
}