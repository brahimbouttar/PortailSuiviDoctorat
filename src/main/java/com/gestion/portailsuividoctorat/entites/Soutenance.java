package com.gestion.portailsuividoctorat.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Soutenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private String lieu;
    private double note;
    private int nbrPublication;
    private int credits;
    private String demandeManus;
    private String rapport;
    private String rapportAntiPlagiat;
    private String rapportCPC;
    private String attestation;
}
