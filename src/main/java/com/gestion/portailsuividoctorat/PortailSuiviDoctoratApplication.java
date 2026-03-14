package com.gestion.portailsuividoctorat;

import com.gestion.portailsuividoctorat.entites.Utilisateur;
import com.gestion.portailsuividoctorat.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortailSuiviDoctoratApplication  {
//    implements CommandLineRunner
//    @Autowired
//    UtilisateurRepo utilisateurRepo;
    public static void main(String[] args) {
        SpringApplication.run(PortailSuiviDoctoratApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        utilisateurRepo.save(new Utilisateur(1,"brahim","bouttar","brahimbouttar@gmail.com","casablanca","+212677862028","123456","admin"));
//    }
}
