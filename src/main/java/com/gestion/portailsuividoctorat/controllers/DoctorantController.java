package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.services.DoctorantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctorants")
public class DoctorantController {

    @Autowired
    private DoctorantServiceImpl doctorantService;

    @GetMapping
    public String listDoctorants(Model model) {
        model.addAttribute("doctorants", doctorantService.findAllDoctorants());
        model.addAttribute("newDoctorant",
                new Doctorant());
        return "Doctorant/Liste";
    }

    @GetMapping("/Dashboard")
    public String dashboard() {
        return "Doctorant/Dashboard";
    }



    @PostMapping("/save")
    public String createDoctorant(@ModelAttribute("newDoctorant") Doctorant doctorant) {
        doctorant.setRole("DOCTORANT");
        doctorantService.createDoctorant(doctorant);
        return "redirect:/doctorants";
    }
    @PostMapping("/update/{id}")
    public String updateDoctorant(
            @PathVariable Long id,
            @ModelAttribute Doctorant doctorant) {

        doctorantService.updateDoctorant(doctorant, id);

        return "redirect:/doctorants";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("doctorant", doctorantService.findDoctorant(id));
        return "Doctorant/Details";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String deleteDoctorant(@PathVariable Long id) {
        doctorantService.DeleteDoctorant(id);
        return "success";
    }
}