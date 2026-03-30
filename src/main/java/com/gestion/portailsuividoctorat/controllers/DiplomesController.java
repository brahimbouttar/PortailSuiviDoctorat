package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import com.gestion.portailsuividoctorat.services.DiplomesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/Diplome")
@RestController
@Controller
public class DiplomesController {
    @Autowired
    DiplomesServiceImpl DiplomesService;
    @PostMapping("/create")
    public Diplomes CreateDiplomes(@RequestBody Diplomes u) {
        return DiplomesService.createDiplome(u);
    }
    @GetMapping("/details/{id}")
    public Diplomes trouverDiplomes(@PathVariable Long id){
        return DiplomesService.findDiplome(id);
    }
    @GetMapping("/all")
    public List<Diplomes> findAllDiplomess() {
        return DiplomesService.findAllDiplomes();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteDiplomes(@PathVariable Long id){
        DiplomesService.DeleteDiplome(id);
    }
    @PutMapping("/update/{id}")
    public Diplomes UpdateDiplomes(@PathVariable Long id, @RequestBody Diplomes u) {
        return DiplomesService.updateDiplome(u,id);
    }
}
