package com.gestion.portailsuividoctorat.entites;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nbrArticlesQ1Q2;
    private Integer nbrConferences;
    private Integer heuresFormation;

    private String demandeManus;
    private String rapportThese;
    private String rapportAntiPlagiat;
    private String rapportPublications;
    private String attestations;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut = StatutDemande.EN_ATTENTE;

    private LocalDate dateDepot;
    private String observations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorant_id")
    private Doctorant doctorant;

    public enum StatutDemande {
        EN_ATTENTE,
        VALIDEE_DIRECTEUR,
        VALIDEE_ADMIN,
        REFUSEE,
        AUTORISEE
    }
}