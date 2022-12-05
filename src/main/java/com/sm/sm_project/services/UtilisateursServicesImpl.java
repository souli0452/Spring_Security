package com.sm.sm_project.services;

import com.sm.sm_project.modele.Role;
import com.sm.sm_project.modele.Utilisateur;
import com.sm.sm_project.repositories.RoleRepository;
import com.sm.sm_project.repositories.UtilisateurRepository;
import com.sm.sm_project.repositories.UtlisateursServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UtilisateursServicesImpl implements UtlisateursServices {
    private UtilisateurRepository utilisateurRepository;
    private RoleRepository roleRepository;


    public UtilisateursServicesImpl(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public List<Utilisateur> listeUtilisateur() {
        return utilisateurRepository.findAll();
    }




    @Override
    public void utilisateurRole(String username, String nomRole) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        Role role=roleRepository.findRoleByNomRole(nomRole);
        utilisateur.getRoles().add(role);

    }
    @Override
    public Role creerRole(Role role) {

        return roleRepository.save(role);
    }

    public Utilisateur findUtilisateurByUsername(String username){
        return utilisateurRepository.findUtilisateurByUsername(username);
    }
}
