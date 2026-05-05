package com.gestion.portailsuividoctorat.services;


import com.gestion.portailsuividoctorat.entites.Jury;

import java.util.List;

public interface JuryService  {
    Jury createJury(Jury u);
    Jury updateJury(Jury u, Long id);
    Jury findJury(Long id);
    List<Jury> findAllJuries();
    void DeleteJury(Long id);
}