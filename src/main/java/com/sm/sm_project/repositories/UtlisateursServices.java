package com.sm.sm_project.repositories;

import com.sm.sm_project.modele.Role;
import com.sm.sm_project.modele.Utilisateur;

import java.util.List;

public interface UtlisateursServices {
    Utilisateur creerUtilisateur(Utilisateur utilisateur);
    List<Utilisateur> listeUtilisateur();
  void utilisateurRole(String username,String nomRole);
  Role creerRole(Role role);
}
