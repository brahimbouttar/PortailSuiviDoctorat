package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import com.gestion.portailsuividoctorat.services.DiplomesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DiplomesController {
    @Autowired
    DiplomesServiceImpl DiplomesService;

    @GetMapping("/Diplomes")
    public String list(Model model) {
        model.addAttribute("diplomes", DiplomesService.findAllDiplomes());
        return "Diplomes/Liste";
    }

    @PostMapping("/Diplome/create")
    @ResponseBody
    public Diplomes CreateDiplomes(@RequestBody Diplomes u) {
        return DiplomesService.createDiplome(u);
    }

    @GetMapping("/Diplome/details/{id}")
    @ResponseBody
    public Diplomes trouverDiplomes(@PathVariable Long id){
        return DiplomesService.findDiplome(id);
    }

    @GetMapping("/Diplome/all")
    @ResponseBody
    public List<Diplomes> findAllDiplomess() {
        return DiplomesService.findAllDiplomes();
    }

    @DeleteMapping("/Diplome/delete/{id}")
    @ResponseBody
    public void deleteDiplomes(@PathVariable Long id){
        DiplomesService.DeleteDiplome(id);
    }

    @PutMapping("/Diplome/update/{id}")
    @ResponseBody
    public Diplomes UpdateDiplomes(@PathVariable Long id, @RequestBody Diplomes u) {
        return DiplomesService.updateDiplome(u,id);
    }
}
