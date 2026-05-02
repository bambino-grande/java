package ru.pogosian.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.business.users.*;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UUID keycloakId = UUID.fromString(jwtAuthenticationToken.getName());
        try {
            return  userRepository.findByKeycloakId(keycloakId);
        } catch (DomainValidationException e) {
            User currentUser = createUserRoles(UUID.randomUUID(), getCurrentUsername(), keycloakId);
            userRepository.save(currentUser);
            return currentUser;
        }
    }

    static boolean hasRole(String role) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return  jwtAuthenticationToken.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role));
    }

    private String getCurrentUsername() {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return  jwtAuthenticationToken.getPrincipal().toString();
    }

    private User createUserRoles(UUID id, String name, UUID keycloakId) {
        if(hasRole("ROLE_ADMIN"))
            return new SystemAdmin(id, name, keycloakId);
        if(hasRole("ROLE_WAREHOUSE_ADMIN"))
            return new WarehouseAdmin(id, name, keycloakId);
        if(hasRole("ROLE_MANAGER"))
            return new Manager(id, keycloakId, name);
        if(hasRole("ROLE_USER"))
            return new Client(id, name,  keycloakId);
        throw new IllegalArgumentException("you provided not supported role");
    }
}