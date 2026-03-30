package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.services.DoctorantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/Doctorant")
@RestController
@Controller
public class DoctorantController {
    @Autowired
    DoctorantServiceImpl DoctorantService;
    @PostMapping ("/create")
    public Doctorant CreateDoctorant(@RequestBody Doctorant u) {
        return DoctorantService.createDoctorant(u);
    }
    @GetMapping("/details/{id}")
    public Doctorant trouverDoctorant(@PathVariable Long id){
        return DoctorantService.findDoctorant(id);
    }
    @GetMapping("/all")
    public List<Doctorant> findAllDoctorants() {
        return DoctorantService.findAllDoctorants();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteDoctorant(@PathVariable Long id){
        DoctorantService.DeleteDoctorant(id);
    }
    @PutMapping("/update/{id}")
    public Doctorant UpdateDoctorant(@PathVariable Long id, @RequestBody Doctorant u) {
        return DoctorantService.updateDoctorant(u,id);
    }
}
