package com.discovery.eureka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Valores desde el archivo de configuración (application.properties o application.yml)
    @Value("${app.eureka.username}")
    private String eurekaUsername;

    @Value("${app.eureka.password}")
    private String eurekaPassword;

    // Configuración del SecurityFilterChain con Spring Boot 3.x
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.csrf(csrf -> csrf.disable()) // Desactiva CSRF si no es necesario
            .authorizeHttpRequests((requests) -> requests
                .anyRequest().authenticated()  // Cualquier solicitud debe estar autenticada
            )
            .httpBasic(withDefaults());  // Autenticación básica con configuración predeterminada


        return http.build();
    }

    // UserDetailsService que carga las credenciales para autenticación básica
    @Bean
    protected UserDetailsService userDetailsService() {
        // Aquí creamos el usuario con las credenciales de Eureka desde application.properties
        UserDetails eurekaUser = User.builder()
                .username(eurekaUsername)
                .password("{noop}" + eurekaPassword)  // "{noop}" indica que no estamos cifrando la contraseña
                //.roles("USER")  // Puedes ajustar los roles según sea necesario
                .build();

        return new InMemoryUserDetailsManager(eurekaUser);
    }
}