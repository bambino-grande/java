package ru.pogosian.infrastructure.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.pogosian.business.excrptions.DomainValidationException;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StorageCarClientImpl implements StorageCarClient {
    private final RestClient restClient;

    private void addAuthHeader(HttpHeaders headers) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        headers.setBearerAuth(jwtAuthenticationToken.getToken().getTokenValue());
    }

    @Override
    public CarInstance getCar(UUID carId) {
        CarResponse response = restClient.get()
                .uri("/api/cars/{id}", carId)
                .headers(this::addAuthHeader)
                .retrieve()
                .body(CarResponse.class);

        if(response == null || response.configuration() == null)
            throw new DomainValidationException("Car not found");
        return new CarInstance(
                response.carId(),
                response.configuration().configurationModelId(),
                response.availableForTestDrive()
        );
    }

    @Override
    public void makeCarAvailableForTestDrive(UUID carId, boolean availableForTestDrive) {
        restClient.patch()
                .uri("/api/cars/{id}/test-drive-availability?available={available}", carId, availableForTestDrive)
                .headers(this::addAuthHeader)
                .retrieve()
                .toBodilessEntity();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record CarConfigurationResponse(
            UUID configurationModelId
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record CarResponse(
        UUID carId,
        CarConfigurationResponse configuration,
        boolean availableForTestDrive
        ){}
}
