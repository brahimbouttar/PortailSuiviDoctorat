package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepo extends JpaRepository<Demande, Long> {
    long countByStatut(Demande.StatutDemande statut);

    List<Demande> findTop5ByStatutOrderByDateDepotDescIdDesc(Demande.StatutDemande statut);

    List<Demande> findByDoctorantId(Long doctorantId);
}
