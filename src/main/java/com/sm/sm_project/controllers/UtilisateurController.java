package com.sm.sm_project.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.sm_project.modele.Role;
import com.sm.sm_project.modele.Utilisateur;
import com.sm.sm_project.security.SecurityConstants;
import com.sm.sm_project.services.UtilisateursServicesImpl;
import com.sm.sm_project.urls.Ulrl;
import lombok.Data;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UtilisateurController {

    private UtilisateursServicesImpl utilisateursServices;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UtilisateurController(UtilisateursServicesImpl utilisateursServices, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.utilisateursServices = utilisateursServices;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


@GetMapping(Ulrl.ALL_USERS)
    public ResponseEntity<List<Utilisateur>> listeUtlisateur(){
        List<Utilisateur> utilisateurs= utilisateursServices.listeUtilisateur();
        return ResponseEntity.status(HttpStatus.OK).body(utilisateurs);
    }
    @PostMapping(Ulrl.ADD_USER)
    public ResponseEntity<Utilisateur> creerUtilisateur(@RequestBody Utilisateur utilisateur){
        String hashPass=bCryptPasswordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(hashPass);
        Utilisateur utilisateur1=utilisateursServices.creerUtilisateur(utilisateur);
        return ResponseEntity.status(HttpStatus.OK).body(utilisateur1);
    }


    @PostMapping(Ulrl.ADD_ROLE_TO_USER)
    public ResponseEntity<?> ajoutRoleUtilisateur(@RequestBody RoleUserForm roleUserForm){
        utilisateursServices.utilisateurRole(roleUserForm.getUsername(),roleUserForm.getNomRole());
        return ResponseEntity.ok().body(" role ajoute effectuer");
    }

    @PostMapping(Ulrl.ADD_ROLE)
    public ResponseEntity<Role> creerProjet(@RequestBody Role role){
        Role role1 =utilisateursServices.creerRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }
@GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String authorizationToken=request.getHeader("Authorization");
        if (authorizationToken !=null && authorizationToken.startsWith("Bearer ")){
            try {
                String jwt=authorizationToken.substring(7);
                Algorithm algorithm=Algorithm.HMAC256(SecurityConstants.SECRET);
                JWTVerifier verifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT= verifier.verify(jwt);
                String username=decodedJWT.getSubject();
               Utilisateur utilisateur=utilisateursServices.findUtilisateurByUsername(username);
                String jwtAccessToken= JWT.create()
                        .withSubject(utilisateur.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",utilisateur.getRoles().stream().map(grantedAuthority -> grantedAuthority.getNomRole()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> idToken= new HashMap<>();
                idToken.put("access-Token",jwtAccessToken);
                idToken.put("refresh-token",authorizationToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);

            }catch (Exception  e){
               throw e;
            }
        }
        else {
            throw new RuntimeException("refresh token requis");
        }
    }

}
@Data
class RoleUserForm{
    private String username;
    private String nomRole;
}