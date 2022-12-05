package com.sm.sm_project.repositories;

import com.sm.sm_project.modele.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
    Utilisateur findUtilisateurByUsername(String username);
}
