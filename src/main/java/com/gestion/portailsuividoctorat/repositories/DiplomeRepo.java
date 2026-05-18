package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiplomeRepo extends JpaRepository<Diplomes, Long> {
}