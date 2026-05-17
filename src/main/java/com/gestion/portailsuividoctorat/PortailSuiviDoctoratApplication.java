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

    @Autowired UtilisateurRepo utilisateurRepo;
    @Autowired EncadrantRepo   encadrantRepo;
    @Autowired DoctorantRepo   doctorantRepo;

    public static void main(String[] args) {
        SpringApplication.run(PortailSuiviDoctoratApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

            Utilisateur admin = new Utilisateur(
                    "ADMIN",
                    "admin123",
                    "+212600000001",
                    "Rabat",
                    "Admin",
                    "admin@portail.ma",
                    "azerty"
            );
            utilisateurRepo.save(admin);



            Encadrant encadrant2 = new Encadrant();
            encadrant2.setRole("ENCADRANT");   // ← must match switch case
            encadrant2.setPassword("enc123");
            encadrant2.setEmail("encadrant@portail.ma");
            encadrant2.setNom("Alaoui");
            encadrant2.setPrenom("Mohamed");
            encadrant2.setTelephone("+212600000002");
            encadrant2.setAdresse("Casablanca");
            encadrant2.setSpecialite("Informatique");
            encadrant2.setGrade("Professeur");
            encadrant2.setEtablissement("FSTM");
            encadrantRepo.save(encadrant2);



            // fetch the encadrant to link
            Encadrant encadrant = encadrantRepo.findByEmail("encadrant@portail.ma");

            Doctorant doctorant = new Doctorant();
            doctorant.setRole("DOCTORANT");   // ← must match switch case
            doctorant.setPassword("doc123");
            doctorant.setEmail("doctorant@portail.ma");
            doctorant.setNom("Bouttar");
            doctorant.setPrenom("Brahim");
            doctorant.setTelephone("+212677862028");
            doctorant.setAdresse("Casablanca");
            doctorant.setSujetThese("Intelligence artificielle appliquée");
            doctorant.setStatut("ACTIF");
            doctorant.setEncadrant(encadrant);  // ← linked to encadrant
            doctorantRepo.save(doctorant);

    }
}