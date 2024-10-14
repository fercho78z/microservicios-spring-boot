package com.api.gateway.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Obtener el mapa "realm_access" del JWT
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        
        if (realmAccess == null || realmAccess.isEmpty()) {
            return Collections.emptyList();  // Si no hay roles, devolver una lista vacía
        }

        // Intentar obtener la lista de roles y asegurar que sea una lista de cadenas (List<String>)
        Object roles = realmAccess.get("roles");
        if (roles instanceof List<?>) {
            List<?> rolesList = (List<?>) roles;
            List<String> rolesAsString = rolesList.stream()
                .filter(role -> role instanceof String)  // Filtrar sólo las cadenas
                .map(Object::toString)
                .collect(Collectors.toList());

            return rolesAsString.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))  // Prefijo 'ROLE_'
                .collect(Collectors.toList());
        }

        return Collections.emptyList();  // Si no hay roles o el tipo no es correcto, devolver vacío
    }
}