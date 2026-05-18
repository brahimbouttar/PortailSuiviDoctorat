package com.gestion.portailsuividoctorat.services;
import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DemandeServiceImpl implements DemandeService {
 //declaration producer


    private final DemandeRepo repository;

    public DemandeServiceImpl(DemandeRepo repository) {
        this.repository = repository;
    }
//     injection constructeur

    @Override
    public Demande createDemande(Demande demande) {
        validerPrerequisSoutenance(demande);
        Demande saved = repository.save(demande);


        return saved;
    }
    @Override
    public Demande updateDemande(Long id, Demande demande) {

        Demande existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable : " + id));

        validerPrerequisSoutenance(demande);

        existing.setNbrArticlesQ1Q2(demande.getNbrArticlesQ1Q2());
        existing.setNbrConferences(demande.getNbrConferences());
        existing.setHeuresFormation(demande.getHeuresFormation());
        existing.setDemandeManus(demande.getDemandeManus());
        existing.setRapportThese(demande.getRapportThese());
        existing.setRapportAntiPlagiat(demande.getRapportAntiPlagiat());
        existing.setRapportPublications(demande.getRapportPublications());
        existing.setAttestations(demande.getAttestations());
        existing.setStatut(demande.getStatut());

        Demande updated = repository.save(existing);




        return updated;
    }

    @Override
    public void deleteDemande(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Demande getDemandeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable : " + id));
    }

    @Override
    public List<Demande> getAllDemandes() {
        return repository.findAll();
    }

    private void validerPrerequisSoutenance(Demande demande) {
        if (demande.getNbrArticlesQ1Q2() == null || demande.getNbrArticlesQ1Q2() < 2) {
            throw new RuntimeException(
                    "Prérequis non rempli : au moins 2 articles journaux Q1/Q2 requis.");
        }
        if (demande.getNbrConferences() == null || demande.getNbrConferences() < 2) {
            throw new RuntimeException(
                    "Prérequis non rempli : au moins 2 conférences (ou équivalent) requises.");
        }
        if (demande.getHeuresFormation() == null || demande.getHeuresFormation() < 200) {
            throw new RuntimeException(
                    "Prérequis non rempli : 200h de formation doctorale requises.");
        }
    }
}