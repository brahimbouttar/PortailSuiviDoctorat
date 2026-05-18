package com.gestion.portailsuividoctorat.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Doctorant extends Utilisateur {
    private String SujetThese;
    private String CV;
    private String LettreMotivation;

    public Doctorant(String role, String password, String telephone, String adresse, String prenom, String email,
            String nom,
            String SujetThese, String CV, String LettreMotivation) {
        super(nom, prenom, email, telephone, password, role, adresse);
        this.SujetThese = SujetThese;
        this.CV = CV;
        this.LettreMotivation = LettreMotivation;
    }

    @ManyToOne
    private Utilisateur encadrant;
}
