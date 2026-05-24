package com.gestion.portailsuividoctorat;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Encadrant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
import com.gestion.portailsuividoctorat.repositories.EncadrantRepo;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortailSuiviDoctoratApplication implements CommandLineRunner {
    @Autowired
    UtilisateurRepo utilisateurRepo;
    @Autowired
    DoctorantRepo doctorantRepo;
    @Autowired
    EncadrantRepo encadrantRepo;
    public static void main(String[] args) {
        SpringApplication.run(PortailSuiviDoctoratApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (utilisateurRepo.findByEmail("admin@portail.local").isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("Portail");
            admin.setEmail("admin@portail.local");
            admin.setAdresse("Administration");
            admin.setTelephone("+212600000000");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            utilisateurRepo.save(admin);
        }

        if (utilisateurRepo.findByEmail("doctorant@portail.local").isEmpty()) {
            Doctorant doctorant = new Doctorant();
            doctorant.setNom("Doctorant");
            doctorant.setPrenom("Demo");
            doctorant.setEmail("doctorant@portail.local");
            doctorant.setAdresse("Faculte des sciences");
            doctorant.setTelephone("+212611111111");
            doctorant.setPassword("doctorant123");
            doctorant.setRole("DOCTORANT");
            doctorant.setSujetThese("Portail de suivi doctoral");
            doctorant.setCV("CV demo");
            doctorant.setLettreMotivation("Lettre demo");
            doctorantRepo.save(doctorant);
        }

        if (encadrantRepo.findByEmail("encadrant@portail.local") == null) {
            Encadrant encadrant = new Encadrant();
            encadrant.setNom("Encadrant");
            encadrant.setPrenom("Demo");
            encadrant.setEmail("encadrant@portail.local");
            encadrant.setAdresse("Faculte des sciences");
            encadrant.setTelephone("+212622222222");
            encadrant.setPassword("encadrant123");
            encadrant.setRole("ENCADRANT");
            encadrant.setSpecialite("Informatique");
            encadrant.setGrade("Professeur");
            encadrant.setEtablissement("Faculte des sciences");
            encadrantRepo.save(encadrant);
        }
    }

}
