package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Jury;
import com.gestion.portailsuividoctorat.services.JuryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/Jury")
@RestController
@Controller
public class JuryController {
    @Autowired
    JuryServiceImpl JuryService;
    @PostMapping ("/create")
    public Jury CreateJury(@RequestBody Jury u) {
        return JuryService.createJury(u);
    }
    @GetMapping("/details/{id}")
    public Jury trouverJury(@PathVariable Long id){
        return JuryService.findJury(id);
    }
    @GetMapping("/all")
    public List<Jury> findAllJurys() {
        return JuryService.findAllJuries();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteJury(@PathVariable Long id){
        JuryService.DeleteJury(id);
    }
    @PutMapping("/update/{id}")
    public Jury UpdateJury(@PathVariable Long id, @RequestBody Jury u) {
        return JuryService.updateJury(u,id);
    }
}
