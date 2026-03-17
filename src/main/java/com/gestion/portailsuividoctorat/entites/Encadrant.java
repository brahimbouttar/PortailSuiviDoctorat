package com.gestion.portailsuividoctorat.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Encadrant extends Utilisateur {
    private String specialite;
    private String grade;
    private String Etablissement;

}