package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoutenanceRepo extends JpaRepository<Soutenance, Long> {
    List<Soutenance> findByLieu(String lieu);
}
