package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import com.gestion.portailsuividoctorat.entites.Doctorant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomeRepo extends JpaRepository<Diplomes,Long> {
}
