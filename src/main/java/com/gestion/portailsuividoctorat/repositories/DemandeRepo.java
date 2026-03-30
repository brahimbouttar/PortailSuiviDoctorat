package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepo extends JpaRepository<Demande,Long> {
}
