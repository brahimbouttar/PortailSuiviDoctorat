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
@DiscriminatorValue("ENCADRANT")
public class Encadrant extends Utilisateur {

    private String specialite;
    private String grade;

    public Encadrant(String nom, String prenom, String email,
                     String tel, String password, String role,
                     String adresse, String specialite, String grade) {
        super(nom, prenom, email, tel, password, role, adresse);
        this.specialite = specialite;
        this.grade = grade;
    }
}