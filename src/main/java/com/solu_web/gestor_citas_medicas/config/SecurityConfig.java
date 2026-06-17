package com.solu_web.gestor_citas_medicas.config;

import com.solu_web.gestor_citas_medicas.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/**").permitAll()

        .requestMatchers("/usuarios/**").hasRole("Administrador")
        .requestMatchers("/roles/**").hasRole("Administrador")

        .requestMatchers("/doctores/**").hasAnyRole("Administrador", "Doctor")
        .requestMatchers("/pacientes/**").hasAnyRole("Administrador", "Doctor", "Paciente")
        .requestMatchers("/citas_medicas/**").hasAnyRole("Administrador", "Doctor", "Paciente")

        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}