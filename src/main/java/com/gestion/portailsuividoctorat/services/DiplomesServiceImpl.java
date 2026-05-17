package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import com.gestion.portailsuividoctorat.repositories.DiplomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomesServiceImpl implements DiplomesService {
    @Autowired
    DiplomeRepo DiplomeRepo;
    @Override
    public Diplomes createDiplome(Diplomes d) {
        return DiplomeRepo.save(d);
    }

    @Override
    public Diplomes updateDiplome(Diplomes d, Long id) {
        Diplomes existing = DiplomeRepo.findById(id).orElseThrow(() -> new RuntimeException("Diplome not found"));
        if (d.getBac() != null) {existing.setBac(d.getBac());}
        if (d.getLicence() != null) {existing.setLicence(d.getLicence());}
        if (d.getMaster() != null) {existing.setMaster(d.getMaster());}
        return DiplomeRepo.save(existing);
    }

    @Override
    public Diplomes findDiplome(Long id) {
        return DiplomeRepo.findById(id).orElseThrow(() -> new RuntimeException("Diplome not found"));
    }
    @Override
    public List<Diplomes> findAllDiplomes() {
        return DiplomeRepo.findAll();
    }
    @Override
    public void DeleteDiplome(Long id) {
        Diplomes user = DiplomeRepo.findById(id).orElseThrow(() -> new RuntimeException("Diplome not found"));
        DiplomeRepo.delete(user);
    }
}
