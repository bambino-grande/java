package ru.pogosian.infrastructure.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.excrptions.StorageServiceUnavailableException;
import ru.pogosian.business.services.CarService;
import ru.pogosian.grpc.storage.StorageCarServiceGrpc;
import ru.pogosian.grpc.storage.StorageCars;
import ru.pogosian.infrastructure.grpc.mapper.StorageCarGrpcMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class StorageCarGrpcService extends StorageCarServiceGrpc.StorageCarServiceImplBase{
    private final CarService carService;
    private final StorageCarGrpcMapper storageCarGrpcMapper;

    @Override
    public void listAvailableCars(StorageCars.ListAvailableCarsRequest request, StreamObserver<StorageCars.ListAvailableCarsResponse> responseObserver){
        log.info("gRPC received listAvailableCars");
        List<Car> cars = carService.viewAvailableCars();
        responseObserver.onNext(storageCarGrpcMapper.toListAvailableCarsResponse(cars));
        responseObserver.onCompleted();
    }

    @Override
    public void getAvailableCar(StorageCars.GetAvailableCarRequest request, StreamObserver<StorageCars.AvailableCarResponse> responseObserver){
        log.info("gRPC received GetAvailableCar request: get available car {}", request.getCarId());
        UUID carId = UUID.fromString(request.getCarId());
        Car car = carService.viewAvailableCar(carId);
        responseObserver.onNext(storageCarGrpcMapper.toAvailableCarResponse(car));
        responseObserver.onCompleted();
    }
}
