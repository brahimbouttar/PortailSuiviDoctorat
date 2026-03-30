package com.gestion.portailsuividoctorat.repositories;

import com.gestion.portailsuividoctorat.entites.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Long> {
}
