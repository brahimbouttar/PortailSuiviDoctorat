package com.gestion.portailsuividoctorat.entites;

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
public class Jury extends Utilisateur {
    private String nomJury;
    private String type;
}
