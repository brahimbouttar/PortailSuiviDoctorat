package com.gestion.portailsuividoctorat.kafka;

import com.gestion.portailsuividoctorat.entites.Demande.StatutDemande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeEvent {

    private Long demandeId;

    private Integer nbrArticlesQ1Q2;
    private Integer nbrConferences;
    private Integer heuresFormation;

    private String demandeManus;
    private String rapportThese;
    private String rapportAntiPlagiat;
    private String rapportPublications;
    private String attestations;

    private StatutDemande statut;
    private LocalDate dateDepot;
    private String observations;

    private Long doctorantId;
    private String nomDoctorant;
    private String emailDoctorant;

    private String roleAction;
}