package com.sm.sm_project;

import com.sm.sm_project.modele.Role;
import com.sm.sm_project.modele.Utilisateur;
import com.sm.sm_project.repositories.RoleRepository;
import com.sm.sm_project.repositories.UtilisateurRepository;
import com.sm.sm_project.services.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SmProjectApplication {
	@Autowired
private EmailSendService emailSendService;
	public static void main(String[] args) {
		SpringApplication.run(SmProjectApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
/*	@EventListener(ApplicationReadyEvent.class)
public  void sendMail(){
		emailSendService.sendEmail("merle19@ethereal.email","test","spring Email");
}
*/
}
