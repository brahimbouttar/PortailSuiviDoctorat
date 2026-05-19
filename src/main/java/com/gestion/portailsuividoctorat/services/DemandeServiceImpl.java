package com.gestion.portailsuividoctorat.services;
import com.gestion.portailsuividoctorat.entites.Demande;
import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.repositories.DemandeRepo;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class DemandeServiceImpl implements DemandeService {


    private final DemandeRepo demandeRepository;
    private final DoctorantRepo doctorantRepository;

    public DemandeServiceImpl(DemandeRepo demandeRepository,
                              DoctorantRepo doctorantRepository) {
        this.demandeRepository = demandeRepository;
        this.doctorantRepository = doctorantRepository;
    }
//     injection constructeur

    @Override
    public Demande createDemande(Demande demande, Long doctorantId) {
        Doctorant doctorant = doctorantRepository.findById(doctorantId)
                .orElseThrow(() -> new RuntimeException("Doctorant introuvable"));
        demande.setDoctorant(doctorant);
        demande.setDateDepot(LocalDate.now());
        demande.setStatut(Demande.StatutDemande.EN_ATTENTE);
        return demandeRepository.save(demande);
    }
    @Override
    public List<Demande> getDemandesByDoctorant(Long doctorantId) {
        return demandeRepository.findByDoctorantId(doctorantId);
    }

    @Override
    public Demande createDemande(Demande demande) {
        return null;
    }

    @Override
    public Demande updateDemande(Long id, Demande demande) {

        Demande existing = demandeRepository.findById(id)
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

        Demande updated = demandeRepository.save(existing);


        return updated;
    }

    @Override
    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }

    @Override
    public Demande getDemandeById(Long id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable : " + id));
    }

    @Override
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
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