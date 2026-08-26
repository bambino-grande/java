package ru.pogosian.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import ru.pogosian.business.orders.complectationCarOrder.*;
import ru.pogosian.business.repositories.ComplectationCarOrderRepository;
import ru.pogosian.business.repositories.InStockCarOrderRepository;
import ru.pogosian.business.repositories.UserRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderSecurityService {
    private final InStockCarOrderRepository inStockCarOrderRepository;
    private final ComplectationCarOrderRepository complectationCarOrderRepository;
    private final UserRepository userRepository;

    public boolean isInStockCarOrderOwner(UUID id, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        UUID keycloakId = UUID.fromString(jwtAuthenticationToken.getToken().getSubject());
        UUID clientId = inStockCarOrderRepository.findById(id).getClientId();

        return userRepository.findById(clientId)
                .getKeycloakId()
                .equals(keycloakId);
    }

    public boolean isComplectationCarOrderOwner(UUID id, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        UUID keycloakId = UUID.fromString(jwtAuthenticationToken.getToken().getSubject());
        UUID clientId = complectationCarOrderRepository.findById(id).getClientId();

        return userRepository.findById(clientId)
                .getKeycloakId()
                .equals(keycloakId);
    }

    public boolean canMoveInStockCarOrder(UUID id, Authentication authentication) {
        boolean isAdmin = hasRole(authentication, "ROLE_ADMIN");
        boolean isManager = hasRole(authentication, "ROLE_MANAGER");

        return isAdmin || isManager;
    }

    public boolean canMoveComplectationCarOrder(UUID id, Authentication authentication) {
        boolean isAdmin = hasRole(authentication, "ROLE_ADMIN");
        boolean isWarehouseAdmin = hasRole(authentication, "ROLE_WAREHOUSE_ADMIN");
        boolean isManager = hasRole(authentication, "ROLE_MANAGER");

        if(isAdmin)
            return true;
        CompectationCarOrderStatusState state = complectationCarOrderRepository.findById(id).getState();
        if(isManager && (state instanceof ComplectationCarOrderAwaitingForPaymen
                || state instanceof ComplectationCarOrderPayed
                || state instanceof ComplectationCarOrderPlaced
                || state instanceof ComplectationCarOrderIsReadyForPickingUp
                || state instanceof ComplectationCarOrderCancelled
                || state instanceof ComplectationCarOrderCompleted
        ))
            return true;
        if(isWarehouseAdmin && (state instanceof ComplectationCarOrderAwaitingForShipping
                ||state instanceof ComplectationCarOrderApprovedByWarehouseState
                ||state instanceof ComplectationCarOrderCancelled
        ))
            return true;
        return false;
    }


    public boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(role));
    }
}