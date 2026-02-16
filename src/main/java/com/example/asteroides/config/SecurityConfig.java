package com.example.asteroides.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración básica de seguridad de la web.
 */
@Configuration
public class SecurityConfig {

    /**
        * Define qué rutas son públicas y cómo funciona el login/logout.
        *
        * @param http objeto de configuración de seguridad
        * @return cadena de filtros de seguridad lista para usar
        * @throws Exception si hay un fallo al construir la configuración
        */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/*.png").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }
}