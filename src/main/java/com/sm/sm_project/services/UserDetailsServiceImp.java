package com.sm.sm_project.services;

import com.sm.sm_project.modele.Role;
import com.sm.sm_project.modele.Utilisateur;
import com.sm.sm_project.repositories.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
   private UtilisateurRepository utilisateurRepository;
   private   BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceImp(UtilisateurRepository utilisateurRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("......................................... on est rentree");
        Utilisateur utilisateur=utilisateurRepository.findUtilisateurByUsername(username);
        if (utilisateur==null){
            throw new UsernameNotFoundException(username);
        }
        Collection<GrantedAuthority> authorities= new ArrayList<>();
        for (Role r: utilisateur.getRoles()){
            authorities.add(new SimpleGrantedAuthority(r.getNomRole()));
        }
        return new User(utilisateur.getUsername(),utilisateur.getPassword(),authorities);
    }
}
