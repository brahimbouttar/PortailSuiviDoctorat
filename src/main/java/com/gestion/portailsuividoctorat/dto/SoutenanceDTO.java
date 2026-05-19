package com.gestion.portailsuividoctorat.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoutenanceDTO {

    private Long id;
    private LocalDate date;
    private String lieu;
    private Double note;
    private Integer nbrPublication;
}