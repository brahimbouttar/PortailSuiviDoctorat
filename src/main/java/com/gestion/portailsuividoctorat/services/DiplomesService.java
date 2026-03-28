package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import com.gestion.portailsuividoctorat.entites.Diplomes;

import java.util.List;

public interface DiplomesService {
    //creation
    Diplomes createDiplome(Diplomes u);
    //modification
    Diplomes updateDiplome(Diplomes u, Long id);
    //lecture
    Diplomes findDiplome(Long id);
    //list all
    List<Diplomes> findAllDiplomes();
    //Suppression
    void DeleteDiplome(Long id);
}
