package ru.pogosian;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import ru.pogosian.grpc.storage.StorageCarServiceGrpc;
import ru.pogosian.grpc.storage.StorageCars;
import ru.pogosian.infrastructure.client.StorageCarClient;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@TestPropertySource(properties = {
        "storage-service.grpc.host=localhost",
        "storage-service.grpc.port=19093"
})
public class StorageGrpcClientIntegrationTest extends BaseIntegrationTest {
    private Server server;
    private static volatile ResponseMode responseMode = ResponseMode.SUCCESS;

    @Autowired
    private StorageCarClient storageCarClient;

    @BeforeEach
    void startGrpcServer() throws IOException {
        responseMode = ResponseMode.SUCCESS;
        server = ServerBuilder.forPort(19093)
                .addService(new FakeStorageCarGrpcService())
                .build()
                .start();
    }

    @AfterEach
    void stopGrpcServer() throws InterruptedException {
        if (server != null) {
            server.shutdown();
            server.awaitTermination(1, TimeUnit.SECONDS);
        }
    }

    private enum ResponseMode {
        SUCCESS,
        EMPTY
    }

    @Test
    void getAvailableCarsReturnsCarsFromGrpc() {
        List<StorageCarClient.AvailableCar> cars = storageCarClient.getAvailableCars();

        Assertions.assertEquals(1, cars.size());
        StorageCarClient.AvailableCar car = cars.getFirst();

        Assertions.assertEquals(UUID.fromString("40000000-0000-0000-0000-000000000001"), car.carId());
    }

    @Test
    void getAvailableCarReturnsCarFromGrpc() {
        StorageCarClient.AvailableCar cars = storageCarClient.getAvailableCar(UUID.fromString("40000000-0000-0000-0000-000000000001"));
        Assertions.assertEquals(UUID.fromString("40000000-0000-0000-0000-000000000001"), cars.carId());
    }

    @Test
    void getAvailableCarsReturnsEmptyResult() {
        responseMode = ResponseMode.EMPTY;
        List<StorageCarClient.AvailableCar> cars = storageCarClient.getAvailableCars();
        Assertions.assertEquals(0, cars.size());
    }

    private static class FakeStorageCarGrpcService extends StorageCarServiceGrpc.StorageCarServiceImplBase {
        @Override
        public void listAvailableCars(StorageCars.ListAvailableCarsRequest request, StreamObserver<StorageCars.ListAvailableCarsResponse> responseObserver) {
            StorageCars.ListAvailableCarsResponse.Builder response = StorageCars.ListAvailableCarsResponse.newBuilder();
            if (responseMode == ResponseMode.SUCCESS) {
                response.addCars(StorageCars.AvailableCar.newBuilder()
                                .setCarId("40000000-0000-0000-0000-000000000001")
                                .setCarName("fdfdf")
                                .setConfigurationId("60000000-0000-0000-0000-000000000001")
                                .setConfigurationModelId("30000000-0000-0000-0000-000000000001")
                                .setColor("WHITE")
                                .setPrice("232323")
                                .setAvailableForSale(true)
                                .setAvailableForTestDrive(true)
                                .build())
                        .build();
            }
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        }


        @Override
        public void getAvailableCar(StorageCars.GetAvailableCarRequest request, StreamObserver<StorageCars.AvailableCarResponse> responseObserver) {
            StorageCars.AvailableCarResponse response = StorageCars.AvailableCarResponse.newBuilder()
                    .setCar(StorageCars.AvailableCar.newBuilder()
                        .setCarId("40000000-0000-0000-0000-000000000001")
                        .setCarName("fdfdf")
                        .setConfigurationId("60000000-0000-0000-0000-000000000001")
                        .setConfigurationModelId("30000000-0000-0000-0000-000000000001")
                        .setColor("WHITE")
                        .setPrice("232323")
                        .setAvailableForSale(true)
                        .setAvailableForTestDrive(true)
                        .build())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
