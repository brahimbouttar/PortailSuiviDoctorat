package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorantRepo extends JpaRepository<Doctorant,Long> {
    List<Doctorant> findByEncadrantId(Long encadrantId);
    Doctorant findByEmail(String email);

}
