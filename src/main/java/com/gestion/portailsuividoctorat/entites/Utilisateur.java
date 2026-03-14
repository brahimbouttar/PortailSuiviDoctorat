package com.gestion.portailsuividoctorat.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor //Lombook
@Entity
public class Utilisateur {
    @Id
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String telephone;
    private String password;
    private String role;

    public Utilisateur(long id, String role, String password, String telephone, String adresse, String prenom, String email, String nom) {
        this.id = id;
        this.role = role;
        this.password = password;
        this.telephone = telephone;
        this.adresse = adresse;
        this.prenom = prenom;
        this.email = email;
        this.nom = nom;
    }
}
