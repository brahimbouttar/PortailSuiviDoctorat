package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorantServiceImpl implements DoctorantService {
    @Autowired
    DoctorantRepo DoctorantRepo;
    @Override
    public Doctorant createDoctorant(Doctorant d) {
        return DoctorantRepo.save(d);
    }

    @Override
    public Doctorant updateDoctorant(Doctorant d, Long id) {
        Doctorant existing = DoctorantRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctorant not found"));
        if (d.getSujetThese() != null) {existing.setSujetThese(d.getSujetThese());}
        if (d.getCV() != null) {existing.setCV(d.getCV());}
        if (d.getLettreMotivation() != null) {existing.setLettreMotivation(d.getLettreMotivation());}
        return DoctorantRepo.save(existing);
    }

    @Override
    public Doctorant findDoctorant(Long id) {
        return DoctorantRepo.findById(id).orElseThrow();
    }
    @Override
    public List<Doctorant> findAllDoctorants() {
        return DoctorantRepo.findAll();
    }
    @Override
    public void DeleteDoctorant(Long id) {
        Doctorant user = DoctorantRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctorant not found"));
        DoctorantRepo.delete(user);
    }
}
