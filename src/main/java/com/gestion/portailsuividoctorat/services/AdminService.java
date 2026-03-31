package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;

import java.util.List;

public interface AdminService {
    List<Utilisateur> getAllUsers();
    void assignRole(long userId, String role);
    List<Doctorant> getAllDoctorants();
    Doctorant getDoctorant(long id);
    void deleteDoctorant(long id);
    void assignSupervisor(long doctorantId, long encadrantId);

}
