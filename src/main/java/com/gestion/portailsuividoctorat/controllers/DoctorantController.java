package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.services.DoctorantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctorants")
public class DoctorantController {

    @Autowired
    private DoctorantServiceImpl doctorantService;

    @GetMapping
    public String listDoctorants(Model model) {
        model.addAttribute("doctorants", doctorantService.findAllDoctorants());
        return "doctorant/Liste";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Long id, Model model) {
        Doctorant doctorant = (id != null)
                ? doctorantService.findDoctorant(id)
                : new Doctorant();
        model.addAttribute("doctorant", doctorant);
        return "form";
    }

    @PostMapping("/save")
    public String saveDoctorant(@ModelAttribute Doctorant doctorant) {
        if (doctorant.getId() != 0) {
            doctorantService.updateDoctorant(doctorant, doctorant.getId());
        } else {
            doctorantService.createDoctorant(doctorant);
        }
        return "redirect:/doctorants";
    }

    @GetMapping("/detail")
    public String detailDoctorant(@RequestParam Long id, Model model) {
        model.addAttribute("doctorant", doctorantService.findDoctorant(id));
        return "detail";
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctorant(@PathVariable Long id) {
        doctorantService.DeleteDoctorant(id);
        return "redirect:/doctorants";
    }
}