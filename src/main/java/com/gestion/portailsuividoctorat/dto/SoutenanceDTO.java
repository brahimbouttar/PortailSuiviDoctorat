package com.gestion.portailsuividoctorat.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoutenanceDTO {

    private Long id;           // Long (objet) → peut être null pour un ajout
    private LocalDate date;
    private String lieu;
    private Double note;       // Double → null si champ vide
    private Integer nbrPublication; // Integer → null si champ vide
}