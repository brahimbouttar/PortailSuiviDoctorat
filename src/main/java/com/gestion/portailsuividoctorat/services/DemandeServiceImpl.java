package com.gestion.portailsuividoctorat.services;
import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class DemandeServiceImpl implements DemandeService {


    private final DemandeRepo repository;

    public DemandeServiceImpl(DemandeRepo repository) {
        this.repository = repository;
    }
//     injection constructeur

    @Override
    public Demande createDemande(Demande demande) {
        if (demande.getDateDepot() == null) {
            demande.setDateDepot(LocalDate.now());
        }
        if (demande.getStatut() == null) {
            demande.setStatut(Demande.StatutDemande.EN_ATTENTE);
        }
        validerPrerequisSoutenance(demande);
        return repository.save(demande);
    }
    @Override
    public Demande updateDemande(Long id, Demande demande) {

        Demande existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable : " + id));

        validerPrerequisSoutenance(demande);

        existing.setNbrArticlesQ1Q2(demande.getNbrArticlesQ1Q2());
        existing.setNbrConferences(demande.getNbrConferences());
        existing.setHeuresFormation(demande.getHeuresFormation());
        if (demande.getDemandeManus() != null) {
            existing.setDemandeManus(demande.getDemandeManus());
        }
        if (demande.getRapportThese() != null) {
            existing.setRapportThese(demande.getRapportThese());
        }
        if (demande.getRapportAntiPlagiat() != null) {
            existing.setRapportAntiPlagiat(demande.getRapportAntiPlagiat());
        }
        if (demande.getRapportPublications() != null) {
            existing.setRapportPublications(demande.getRapportPublications());
        }
        if (demande.getAttestations() != null) {
            existing.setAttestations(demande.getAttestations());
        }
        if (demande.getStatut() != null) {
            existing.setStatut(demande.getStatut());
        }
        if (demande.getObservations() != null) {
            existing.setObservations(demande.getObservations());
        }
        if (demande.getDoctorant() != null) {
            existing.setDoctorant(demande.getDoctorant());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteDemande(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Demande introuvable : " + id);
        }
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

    @Override
    public Demande changerStatut(Long id, Demande.StatutDemande statut) {
        Demande demande = getDemandeById(id);
        demande.setStatut(statut);
        return repository.save(demande);
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
