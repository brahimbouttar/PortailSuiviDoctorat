package com.gestion.portailsuividoctorat.controllers;

import com.gestion.portailsuividoctorat.entites.Diplomes;
import com.gestion.portailsuividoctorat.services.DiplomesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class DiplomesController {

    @Autowired
    private DiplomesServiceImpl diplomesService;

    private final String uploadDir = "uploads/";

    @GetMapping("/Diplomes")
    public String listDiplomes(Model model) {
        model.addAttribute("diplomes", diplomesService.findAllDiplomes());
        return "Diplomes/Liste";
    }

    @PostMapping("/Diplome/create")
    public String createDiplome(
            @RequestParam String bac,
            @RequestParam String licence,
            @RequestParam String master,
            @RequestParam("bacFile") MultipartFile bacFile,
            @RequestParam("licenceFile") MultipartFile licenceFile,
            @RequestParam("masterFile") MultipartFile masterFile
    ) throws IOException {

        Diplomes d = new Diplomes();
        d.setBac(bac);
        d.setLicence(licence);
        d.setMaster(master);

        d.setBacFile(saveFile(bacFile));
        d.setLicenceFile(saveFile(licenceFile));
        d.setMasterFile(saveFile(masterFile));

        diplomesService.createDiplome(d);

        return "redirect:/Diplomes";
    }

    @PostMapping("/Diplome/update/{id}")
    public String updateDiplome(
            @PathVariable Long id,
            @RequestParam String bac,
            @RequestParam String licence,
            @RequestParam String master,
            @RequestParam(required = false) MultipartFile bacFile,
            @RequestParam(required = false) MultipartFile licenceFile,
            @RequestParam(required = false) MultipartFile masterFile
    ) throws IOException {

        Diplomes d = new Diplomes();
        d.setBac(bac);
        d.setLicence(licence);
        d.setMaster(master);

        if (!bacFile.isEmpty()) {
            d.setBacFile(saveFile(bacFile));
        }

        if (!licenceFile.isEmpty()) {
            d.setLicenceFile(saveFile(licenceFile));
        }

        if (!masterFile.isEmpty()) {
            d.setMasterFile(saveFile(masterFile));
        }

        diplomesService.updateDiplome(d, id);

        return "redirect:/Diplomes";
    }

    @GetMapping("/Diplome/delete/{id}")
    public String deleteDiplome(@PathVariable Long id) {
        diplomesService.deleteDiplome(id);
        return "redirect:/Diplomes";
    }

    private String saveFile(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }

        // absolute path in your project
        String uploadPath = System.getProperty("user.dir") + File.separator + "uploads";

        File directory = new File(uploadPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        File destination = new File(directory, fileName);

        file.transferTo(destination);

        return fileName;
    }
}