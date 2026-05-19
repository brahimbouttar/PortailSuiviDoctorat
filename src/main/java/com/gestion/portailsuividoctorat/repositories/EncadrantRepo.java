package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncadrantRepo extends JpaRepository<Encadrant,Long> {
    Encadrant findByEmail(String email);

}
