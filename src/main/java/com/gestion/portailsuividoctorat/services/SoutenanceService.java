package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.dto.SoutenanceDTO;

import java.util.List;

public interface SoutenanceService {

    List<SoutenanceDTO> getAllSoutenance();

    SoutenanceDTO getSoutenance(long id);

    List<SoutenanceDTO> getSoutenanceByLieu(String lieu);

    SoutenanceDTO createSoutenance(SoutenanceDTO dto);

    SoutenanceDTO updateSoutenance(long id, SoutenanceDTO dto);

    void deleteSoutenance(long id);
}