package com.gestion.portailsuividoctorat;

import com.gestion.portailsuividoctorat.entites.Doctorant;
import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.DoctorantRepo;
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
    public static void main(String[] args) {
        SpringApplication.run(PortailSuiviDoctoratApplication.class, args);
    }
    Utilisateur u1 = new Utilisateur("Utilisateur","bouttar","brahimbouttar@gmail.com","casablanca","+212677862028","123456","admin");
    Doctorant d1 = new Doctorant("brahim","bouttar","brahimbouttar@gmail.com","casablanca","+212677862028","123456","admin","hello","hhhhh","dhhjfk");

    @Override
    public void run(String... args) throws Exception {
        utilisateurRepo.save(u1);
        doctorantRepo.save(d1);
    }

}
