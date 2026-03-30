package com.gestion.portailsuividoctorat.services;

import com.gestion.portailsuividoctorat.entites.Jury;
import com.gestion.portailsuividoctorat.repositories.JuryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JuryServiceImpl implements JuryService {
    @Autowired
    JuryRepo JuryRepo;
    @Override
    public Jury createJury(Encadrant u) {
        return JuryRepo.save(u);
    }

    @Override
    public Jury updateJury(Jury u, Long id) {
        Jury existing = JuryRepo.findById(id).orElseThrow(() -> new RuntimeException("Jury not found"));
        if (u.getnomJury() != null) {existing.setnomJury(u.getnomJury());}
        if (u.gettype() != null) {existing.settype(u.gettype());}
        return JuryRepo.save(existing);
    }

    @Override
    public Jury findJury(Long id) {
        return JuryRepo.findById(id).orElseThrow(() -> new RuntimeException("Jury not found"));
    }
    @Override
    public List<Jury> findAllJuries() {
        return JuryRepo.findAll();
    }
    @Override
    public void DeleteJury(Long id) {
        Jury user = JuryRepo.findById(id).orElseThrow(() -> new RuntimeException("Jury not found"));
        JuryRepo.delete(user);
    }
}
