package ru.pogosian.infrastructure.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.excrptions.StorageServiceUnavailableException;
import ru.pogosian.grpc.storage.StorageCarServiceGrpc;
import ru.pogosian.grpc.storage.StorageCars;
import ru.pogosian.infrastructure.client.mapper.StorageCarClientMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageCarClientImpl implements StorageCarClient {
    private final RestClient restClient;
    private final StorageCarServiceGrpc.StorageCarServiceBlockingStub storageCarServiceBlockingStub;
    private final StorageCarClientMapper storageCarClientMapper;

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

    @Override
    public List<AvailableCar> getAvailableCars() {
        log.info("gRPC call: list available cars");
        try {
            return storageCarServiceBlockingStub
                    .withDeadlineAfter(1000, TimeUnit.MILLISECONDS)
                    .listAvailableCars(StorageCars.ListAvailableCarsRequest.getDefaultInstance())
                    .getCarsList()
                    .stream()
                    .map(storageCarClientMapper::toClientCar)
                    .toList();
        } catch (Exception ex) {
            throw new StorageServiceUnavailableException(ex.getMessage());
        }
    }

    @Override
    public AvailableCar getAvailableCar(UUID carId) {
        log.info("gRPC call: get available car {}", carId);
        try {
            return storageCarClientMapper.toClientCar(storageCarServiceBlockingStub
                    .withDeadlineAfter(1000, TimeUnit.MILLISECONDS)
                    .getAvailableCar(StorageCars.GetAvailableCarRequest.newBuilder()
                            .setCarId(carId.toString())
                            .build())
                    .getCar());
        }  catch (Exception ex) {
            throw new StorageServiceUnavailableException(ex.getMessage());
        }
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
