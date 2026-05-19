package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorantRepo extends JpaRepository<Doctorant,Long> {
    List<Doctorant> findByEncadrantId(Long encadrantId);
    Optional<Doctorant> findByNom(String username);

    Doctorant findByEmail(String email);

}
