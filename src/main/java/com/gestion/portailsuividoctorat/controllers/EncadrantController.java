package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.services.EncadrantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/Encadrant")
@RestController
@Controller
public class EncadrantController {
    @Autowired
    EncadrantServiceImpl EncadrantService;
    @PostMapping ("/create")
    public Encadrant CreateEncadrant(@RequestBody Encadrant u) {
        return EncadrantService.createEncadrant(u);
    }
    @GetMapping("/details/{id}")
    public Encadrant trouverEncadrant(@PathVariable Long id){
        return EncadrantService.findEncadrant(id);
    }
    @GetMapping("/all")
    public List<Encadrant> findAllEncadrants() {
        return EncadrantService.findAllEncadrants();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteEncadrant(@PathVariable Long id){
        EncadrantService.DeleteEncadrant(id);
    }
    @PutMapping("/update/{id}")
    public Encadrant UpdateEncadrant(@PathVariable Long id, @RequestBody Encadrant u) {
        return EncadrantService.updateEncadrant(u,id);
    }
}
