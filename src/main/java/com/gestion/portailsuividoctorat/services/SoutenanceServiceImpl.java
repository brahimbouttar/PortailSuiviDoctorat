package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.dto.SoutenanceDTO;
import com.gestion.portailsuividoctorat.entites.Soutenance;
import com.gestion.portailsuividoctorat.repositories.SoutenanceRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoutenanceServiceImpl implements SoutenanceService {

    private final SoutenanceRepo repository;

    public SoutenanceServiceImpl(SoutenanceRepo repository) {
        this.repository = repository;
    }

    private SoutenanceDTO mapToDTO(Soutenance s) {
        SoutenanceDTO dto = new SoutenanceDTO();
        dto.setId(s.getId());
        dto.setDate(s.getDate());
        dto.setLieu(s.getLieu());
        dto.setNote(s.getNote());
        dto.setNbrPublication(s.getNbrPublication());
        return dto;
    }

    private Soutenance mapToEntity(SoutenanceDTO dto) {
        Soutenance s = new Soutenance();
        s.setDate(dto.getDate());
        s.setLieu(dto.getLieu());
        s.setNote(dto.getNote() != null ? dto.getNote() : 0.0);
        s.setNbrPublication(dto.getNbrPublication() != null ? dto.getNbrPublication() : 0);
        return s;
    }

    @Override
    public List<SoutenanceDTO> getAllSoutenance() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SoutenanceDTO getSoutenance(long id) {
        Soutenance s = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soutenance introuvable : " + id));
        return mapToDTO(s);
    }

    @Override
    public List<SoutenanceDTO> getSoutenanceByLieu(String lieu) {
        return repository.findByLieu(lieu)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SoutenanceDTO createSoutenance(SoutenanceDTO dto) {
        validerSoutenance(dto);
        return mapToDTO(repository.save(mapToEntity(dto)));
    }

    @Override
    public SoutenanceDTO updateSoutenance(long id, SoutenanceDTO dto) {
        validerSoutenance(dto);
        Soutenance existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soutenance introuvable : " + id));

        existing.setDate(dto.getDate());
        existing.setLieu(dto.getLieu());
        existing.setNote(dto.getNote() != null ? dto.getNote() : 0.0);
        existing.setNbrPublication(dto.getNbrPublication() != null ? dto.getNbrPublication() : 0);

        return mapToDTO(repository.save(existing));
    }

    @Override
    public void deleteSoutenance(long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Soutenance introuvable : " + id);
        }
        repository.deleteById(id);
    }

    private void validerSoutenance(SoutenanceDTO dto) {
        if (dto.getDate() == null) {
            throw new RuntimeException("Date de soutenance obligatoire");
        }
        if (dto.getLieu() == null || dto.getLieu().isBlank()) {
            throw new RuntimeException("Lieu de soutenance obligatoire");
        }
        int nbPub = dto.getNbrPublication() != null ? dto.getNbrPublication() : 0;
        if (nbPub < 2) {
            throw new RuntimeException("Conditions non respectees : minimum 2 publications requises");
        }
        if (dto.getNote() != null && (dto.getNote() < 0 || dto.getNote() > 20)) {
            throw new RuntimeException("La note doit etre comprise entre 0 et 20");
        }
    }
}
