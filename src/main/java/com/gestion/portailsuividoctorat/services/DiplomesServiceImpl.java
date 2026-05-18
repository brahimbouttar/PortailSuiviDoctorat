package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import com.gestion.portailsuividoctorat.repositories.DiplomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomesServiceImpl implements DiplomesService {

    @Autowired
    private DiplomeRepo diplomeRepo;

    @Override
    public Diplomes createDiplome(Diplomes d) {
        return diplomeRepo.save(d);
    }

    @Override
    public Diplomes updateDiplome(Diplomes d, Long id) {

        Diplomes existing = diplomeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diplome not found"));

        existing.setBac(d.getBac());
        existing.setLicence(d.getLicence());
        existing.setMaster(d.getMaster());

        if (d.getBacFile() != null) {
            existing.setBacFile(d.getBacFile());
        }

        if (d.getLicenceFile() != null) {
            existing.setLicenceFile(d.getLicenceFile());
        }

        if (d.getMasterFile() != null) {
            existing.setMasterFile(d.getMasterFile());
        }

        return diplomeRepo.save(existing);
    }

    @Override
    public Diplomes findDiplome(Long id) {
        return diplomeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diplome not found"));
    }

    @Override
    public List<Diplomes> findAllDiplomes() {
        return diplomeRepo.findAll();
    }

    @Override
    public void deleteDiplome(Long id) {
        diplomeRepo.deleteById(id);
    }
}