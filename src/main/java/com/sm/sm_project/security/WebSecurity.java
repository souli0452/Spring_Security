package com.sm.sm_project.security;

import com.sm.sm_project.repositories.UtilisateurRepository;
import com.sm.sm_project.services.UserDetailsServiceImp;
import com.sm.sm_project.urls.Ulrl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {
    private final UserDetailsServiceImp userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UtilisateurRepository utilisateurRepository;

    public WebSecurity(UserDetailsServiceImp userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UtilisateurRepository utilisateurRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.utilisateurRepository = utilisateurRepository;
    }


@Bean
public  SecurityFilterChain configure(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
 http.cors().and()
           .csrf().disable()
            .authorizeRequests().antMatchers("/refreshToken",Ulrl.ADD_USER).permitAll()
         .antMatchers(Ulrl.ALL_USERS).hasAnyAuthority("CDP","CONSULTANT")
         .antMatchers(Ulrl.ADD_ROLE,Ulrl.ADD_ROLE_TO_USER).hasAuthority("ADMIN")
         .anyRequest().authenticated().and()
           .authenticationManager(authenticationManager)
             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
           .addFilter(new JWTAuthenticationFilter(authenticationManager))
         .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    http.headers().frameOptions().disable();

    return http.build();

}

}
