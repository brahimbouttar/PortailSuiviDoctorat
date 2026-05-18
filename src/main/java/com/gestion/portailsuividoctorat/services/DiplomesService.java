package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Diplomes;

import java.util.List;

public interface DiplomesService {

    Diplomes createDiplome(Diplomes d);

    Diplomes updateDiplome(Diplomes d, Long id);

    Diplomes findDiplome(Long id);

    List<Diplomes> findAllDiplomes();

    void deleteDiplome(Long id);
}