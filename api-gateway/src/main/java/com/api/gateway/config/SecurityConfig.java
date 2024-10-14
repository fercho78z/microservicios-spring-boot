package com.api.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	
	 @Bean
	    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
	        return http
	            .authorizeExchange(exchanges -> exchanges
	                .pathMatchers("/public/**").permitAll() // Endpoints públicos
	                .anyExchange().authenticated() // Cualquier otro endpoint requiere autenticación
	            )
	            .oauth2ResourceServer(oauth2 -> oauth2
	            		 .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))  // JWT para autenticar
	            )
	            .csrf((csrf) -> csrf.disable())  // Deshabilitar CSRF para APIs sin estado
	            .build();
	    }
	 private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
	        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
	        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());  // Usar el convertidor de roles
	        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);  // Adaptamos el convertidor a Reactivo
	    }
}	
/*    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity
        		.csrf((csrf) -> csrf.disable())    // apartir de la v3.3 de spring boot se reqieren expresiones lamba
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                //.oauth2ResourceServer(OAuth2ResourceServerSpec::jwt);
                .oauth2ResourceServer(spec -> spec.jwt(Customizer.withDefaults()));
        return serverHttpSecurity.build();
    }
}*/
/*
@Configuration
public class SecurityConfig {

	@Bean
	@Primary
	@LoadBalanced
    public WebClient builder(){
		 HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
		 return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
	
	}

}
*/