package ru.pogosian;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.*;
import ru.pogosian.grpc.storage.StorageCarServiceGrpc;
import org.springframework.test.context.TestPropertySource;
import ru.pogosian.grpc.storage.StorageCars;

import java.util.UUID;

@TestPropertySource(properties = "storage-service.grpc.port=19092")
public class StorageGrpcServerIntegrationTest extends BaseIntegrationTest {
    private ManagedChannel channel;
    private StorageCarServiceGrpc.StorageCarServiceBlockingStub stub;

    @BeforeEach
    void setUpGrpcClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 19092)
                .usePlaintext()
                .build();
        stub = StorageCarServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    void stopGrpcClient() {
        if(channel != null) {
            channel.shutdown();
        }
    }

    @Test
    public void listAvailableCarsReturnsAvailableCars(){
        StorageCars.ListAvailableCarsResponse response = stub.listAvailableCars(StorageCars.ListAvailableCarsRequest.newBuilder().build());
        Assertions.assertEquals(2, response.getCarsCount());
        Assertions.assertTrue(response.getCarsList().stream().allMatch(StorageCars.AvailableCar::getAvailableForSale));
    }

    @Test
    public void getAvailableCarReturnsCarById(){
        StorageCars.GetAvailableCarRequest request = StorageCars.GetAvailableCarRequest.newBuilder().setCarId(UUID.fromString("40000000-0000-0000-0000-000000000001").toString()).build();
        StorageCars.AvailableCarResponse response = stub.getAvailableCar(request);
        StorageCars.AvailableCar car = response.getCar();

        Assertions.assertEquals(UUID.fromString("40000000-0000-0000-0000-000000000001").toString(), car.getCarId());
    }
}
