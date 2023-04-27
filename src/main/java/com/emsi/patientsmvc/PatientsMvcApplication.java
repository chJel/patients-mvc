package com.emsi.patientsmvc;

import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
   //@Bean On la met on commantaire pour n'est pas executer la commande d'insertion
    @Bean
    CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(
                    new Patient(null,"Hassan",new Date(),false,132));
            patientRepository.save(
                    new Patient(null,"Chaimae",new Date(),true,133));
            patientRepository.save(
                    new Patient(null,"Ayoub",new Date(),false,123));
            patientRepository.save(
                    new Patient(null,"Ammani",new Date(),true,124));
            patientRepository.save(
                    new Patient(null,"Tasnim",new Date(),false,125));
            patientRepository.findAll().forEach(p->{
              System.out.print(p.getNom());
            });

        };
   }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}}
