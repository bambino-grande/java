package ru.pogosian.infrastructure.repository.Mapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.types.Interior;
import ru.pogosian.business.detail.types.SteeringWheel;
import ru.pogosian.business.detail.types.Transmisson;
import ru.pogosian.business.detail.types.Wheel;
import ru.pogosian.business.orders.complectationCarOrder.*;
import ru.pogosian.business.orders.inStockCarOrder.*;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.business.users.*;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailTypes;
import ru.pogosian.infrastructure.repository.JpaEntity.CarJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarModelJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder.ComplectationCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder.ComplectationCarOrderStage;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderStage;
import ru.pogosian.infrastructure.repository.JpaEntity.TestDriveRequestJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserType;

import java.awt.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Mapper {
    /*--------------------------Details--------------------------*/
    public CarDetails toDomain(CarDetailJpaEntity detailJpaEntity) {
        if (detailJpaEntity.getDetailTypes() == CarDetailTypes.Interior) {
            return new Interior(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else if (detailJpaEntity.getDetailTypes() == CarDetailTypes.SteeringWheel) {
            return new SteeringWheel(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else if (detailJpaEntity.getDetailTypes() == CarDetailTypes.Transmission) {
            return new Transmisson(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else if (detailJpaEntity.getDetailTypes() == CarDetailTypes.Wheel) {
            return new Wheel(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else
            return null;
    }

    private CarDetailTypes getDetailType(CarDetails detail) {
        if (detail instanceof Interior)
            return CarDetailTypes.Interior;
        else if (detail instanceof SteeringWheel)
            return CarDetailTypes.SteeringWheel;
        else if (detail instanceof Transmisson)
            return CarDetailTypes.Transmission;
        else if (detail instanceof Wheel)
            return CarDetailTypes.Wheel;
        return null;
    }

    public CarDetailJpaEntity toJpaEntity(CarDetails details) {
        return new CarDetailJpaEntity(
                details.getId(),
                details.getName(),
                details.getDeltaPrice(),
                details.getCompatibleModelsIds(),
                getDetailType(details)
        );
    }

    /*--------------------------Car--------------------------*/
    public Car toDomain(CarJpaEntity carJpaEntity) {
        return Car.builder()
                .carId(carJpaEntity.getId())
                .carName(carJpaEntity.getCarName())
                .configuration(toDomain(carJpaEntity.getConfiguration()))
                .color(carJpaEntity.getColor())
                .price(carJpaEntity.getPrice())
                .availableForSale(carJpaEntity.getAvailableForSale())
                .availableForTestDrive(carJpaEntity.getAvailableForTestDrive())
                .build();
    }



    public CarJpaEntity toJpaEntity(Car car) {
        return new CarJpaEntity(car.getCarId(), car.getCarName(), toJpaEntity(car.getConfiguration()), car.getColor(), car.getPrice(), car.getAvailableForSale(), car.getAvailableForTestDrive());
    }

    /*--------------------------CarConfiguration--------------------------*/
    public CarConfiguration toDomain(CarConfigurationJpaEntity carConfigurationJpaEntity) {
        return CarConfiguration.builder()
                .configurationId(carConfigurationJpaEntity.getId())
                .configurationModelId(carConfigurationJpaEntity.getConfigurationModelId())
                .totalPrice(carConfigurationJpaEntity.getTotalPrice())
                .usedDetails(carConfigurationJpaEntity.getUsedDetails().stream().map(this::toDomain).collect(Collectors.toSet()))
                .build();
    }

    public CarConfigurationJpaEntity toJpaEntity(CarConfiguration carConfiguration) {
        return new CarConfigurationJpaEntity(
            carConfiguration.getConfigurationId(),
            carConfiguration.getConfigurationModelId(),
            carConfiguration.getTotalPrice(),
            carConfiguration.getUsedDetails().stream().map(this::toJpaEntity).collect(Collectors.toSet())
        );

    }

    /*--------------------------CarModel--------------------------*/
    public CarModel toDomain(CarModelJpaEntity carModelJpaEntity) {
        return CarModel.builder()
                .modelId(carModelJpaEntity.getId())
                .modelBrand(carModelJpaEntity.getModelBrand())
                .modelName(carModelJpaEntity.getModelName())
                .bodyType(carModelJpaEntity.getBodyType())
                .availableDetails(carModelJpaEntity.getAvailableDetails().stream().map(this::toDomain).collect(Collectors.toSet()))
                .details(carModelJpaEntity.getDetails().stream().map(this::toDomain).collect(Collectors.toSet()))
                .basePrice(carModelJpaEntity.getBasePrice())
                .fuelType(carModelJpaEntity.getFuelType())
                .horsePower(carModelJpaEntity.getHorsePower())
                .engineVolume(carModelJpaEntity.getEngineVolume())
                .gearboxType(carModelJpaEntity.getGearboxType())
                .driveType(carModelJpaEntity.getDriveType())
                .build();
    }

    public CarModelJpaEntity toJpaEntity(CarModel carModel) {
        return new CarModelJpaEntity(
                carModel.getModelId(),
                carModel.getModelBrand(),
                carModel.getModelName(),
                carModel.getBodyType(),
                carModel.getAvailableDetails().stream().map(this::toJpaEntity).collect(Collectors.toSet()),
                carModel.getBasePrice(),
                carModel.getFuelType(),
                carModel.getHorsePower(),
                carModel.getEngineVolume(),
                carModel.getGearboxType(),
                carModel.getDriveType(),
                carModel.getDetails().stream().map(this::toJpaEntity).collect(Collectors.toSet())
        );
    }
    /*--------------------------InStockCarOrder--------------------------*/
    public InStockCarOrder toDomain(InStockCarOrderJpaEntity inStockCarOrderJpaEntity) {
        return InStockCarOrder.builder()
                .orderId(inStockCarOrderJpaEntity.getId())
                .carId(inStockCarOrderJpaEntity.getCarId())
                .clientId(inStockCarOrderJpaEntity.getClientId())
                .managerId(inStockCarOrderJpaEntity.getManagerId())
                .state(toDomain(inStockCarOrderJpaEntity.getStage()))
                .build();
    }

    public InStockCarOrderJpaEntity toJpaEntity(InStockCarOrder inStockCarOrder) {
        return new InStockCarOrderJpaEntity(
            inStockCarOrder.getOrderId(),
            inStockCarOrder.getClientId(),
            inStockCarOrder.getManagerId(),
            inStockCarOrder.getCarId(),
            toJpaEntity(inStockCarOrder.getState())
        );
    }

    /*--------------------------ComplectationCarOrder--------------------------*/
    public ComplectationCarOrder toDomain(ComplectationCarOrderJpaEntity complectationCarOrderJpaEntity) {
        return ComplectationCarOrder.builder()
                .orderId(complectationCarOrderJpaEntity.getId())
                .clientId(complectationCarOrderJpaEntity.getClientId())
                .managerId(complectationCarOrderJpaEntity.getManagerId())
                .carId(complectationCarOrderJpaEntity.getCarId())
                .state(toDomain(complectationCarOrderJpaEntity.getStage()))
                .build();
    }

    public ComplectationCarOrderJpaEntity toJpaEntity(ComplectationCarOrder complectationCarOrder) {
        return new ComplectationCarOrderJpaEntity(
            complectationCarOrder.getOrderId(),
            complectationCarOrder.getClientId(),
            complectationCarOrder.getManagerId(),
            complectationCarOrder.getCarId(),
            toJpaEntity(complectationCarOrder.getState())
        );
    }
    /*--------------------------CarOrderStages--------------------------*/

    private ComplectationCarOrderStage toJpaEntity(CompectationCarOrderStatusState complectationCarOrderStage) {
        if(complectationCarOrderStage instanceof ComplectationCarOrderApprovedByWarehouseState) {
            return ComplectationCarOrderStage.ApprovedByWarehouse;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderAwaitingForPaymen) {
            return ComplectationCarOrderStage.AwaitingForPayment;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderAwaitingForShipping) {
            return ComplectationCarOrderStage.AwaitingForShipping;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderCancelled) {
            return ComplectationCarOrderStage.Cancelled;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderCompleted) {
            return ComplectationCarOrderStage.Completed;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderIsReadyForPickingUp) {
            return ComplectationCarOrderStage.ReadyForPickingUp;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderPayed) {
            return ComplectationCarOrderStage.Payed;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderPlaced) {
            return ComplectationCarOrderStage.Placed;
        }
        return null;
    }

    private CompectationCarOrderStatusState toDomain(ComplectationCarOrderStage complectationCarOrderStage) {
        if(complectationCarOrderStage == ComplectationCarOrderStage.ApprovedByWarehouse) {
            return new ComplectationCarOrderApprovedByWarehouseState();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.AwaitingForPayment) {
            return new ComplectationCarOrderAwaitingForPaymen();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.AwaitingForShipping) {
            return new ComplectationCarOrderAwaitingForShipping();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Cancelled) {
            return new ComplectationCarOrderCancelled();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Completed) {
            return new ComplectationCarOrderCompleted();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.ReadyForPickingUp) {
            return new ComplectationCarOrderIsReadyForPickingUp();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Payed) {
            return new  ComplectationCarOrderPayed();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Placed) {
            return new ComplectationCarOrderPlaced();
        }
        return null;
    }

    private InStockCarOrderStatusState toDomain(InStockCarOrderStage inStockCarOrderStage) {
        if(inStockCarOrderStage == InStockCarOrderStage.ApprovedByManager) {
            return new InStockCarOrderApprovedByManager();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.AwaitingForPayment) {
            return new  InStockCarOrderAwaitingForPaymen();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Cancelled) {
            return new  InStockCarOrderCancelled();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Completed) {
            return new  InStockCarOrderCompleted();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.ReadyForPickingUp) {
            return new InStockCarOrderIsReadyForPickingUp();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Payed) {
            return new InStockCarOrderPayed();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Placed) {
            return new InStockCarOrderPlaced();
        }
        return null;
    }

    private InStockCarOrderStage toJpaEntity(InStockCarOrderStatusState inStockCarOrderStage) {
        if(inStockCarOrderStage instanceof InStockCarOrderApprovedByManager) {
            return InStockCarOrderStage.ApprovedByManager;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderAwaitingForPaymen) {
            return InStockCarOrderStage.AwaitingForPayment;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderCancelled) {
            return InStockCarOrderStage.Cancelled;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderCompleted) {
            return InStockCarOrderStage.Completed;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderIsReadyForPickingUp) {
            return InStockCarOrderStage.ReadyForPickingUp;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderPayed) {
            return InStockCarOrderStage.Payed;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderPlaced) {
            return InStockCarOrderStage.Placed;
        }
        return null;
    }
    /*--------------------------TestDriveRequest--------------------------*/
    public TestDriveRequest toDomain(TestDriveRequestJpaEntity testDriveRequestJpaEntity) {
        return TestDriveRequest.builder()
                .testDriveId(testDriveRequestJpaEntity.getId())
                .isCarCapableForTestDrive(testDriveRequestJpaEntity.isCarCapableForTestDrive())
                .testDriveStartAt(testDriveRequestJpaEntity.getTestDriveStartAt())
                .modelId(testDriveRequestJpaEntity.getModelId())
                .carId(testDriveRequestJpaEntity.getCarId())
                .clientId(testDriveRequestJpaEntity.getClientId())
                .build();
    }
    public TestDriveRequestJpaEntity toJpaEntity(TestDriveRequest testDriveRequest) {
        return new TestDriveRequestJpaEntity(
            testDriveRequest.getCarId(),
            testDriveRequest.isCarCapableForTestDrive(),
            testDriveRequest.getTestDriveId(),
            testDriveRequest.getClientId(),
            testDriveRequest.getModelId(),
            testDriveRequest.getTestDriveStartAt()
        );
    }

    /*--------------------------TestDriveRequest--------------------------*/
    public User toDomain(UserJpaEntity userJpaEntity) {
        if(userJpaEntity.getType() == UserType.USER)
            return new Client(userJpaEntity.getId(), userJpaEntity.getName(), userJpaEntity.getKeycloakId());
        if(userJpaEntity.getType() == UserType.MANAGER)
            return new Manager(userJpaEntity.getId(), userJpaEntity.getKeycloakId(), userJpaEntity.getName());
        if(userJpaEntity.getType() == UserType.ADMIN)
            return new SystemAdmin(userJpaEntity.getId(), userJpaEntity.getName(),userJpaEntity.getKeycloakId());
        if(userJpaEntity.getType() == UserType.WAREHOUSE_ADMIN)
            return new WarehouseAdmin(userJpaEntity.getId(), userJpaEntity.getName(), userJpaEntity.getKeycloakId());
        return null;
    }

    public UserJpaEntity toJpaEntity(User user) {
        return new UserJpaEntity(
            user.getId(),
            user.getKeycloakId(),
            user.getName(),
            getUserType(user)
        );
    }

    private UserType getUserType(User user){
        if(user instanceof Client)
            return UserType.USER;
        if(user instanceof Manager)
            return UserType.MANAGER;
        if(user instanceof SystemAdmin)
            return UserType.ADMIN;
        if(user instanceof WarehouseAdmin)
            return UserType.WAREHOUSE_ADMIN;
        return null;
    }
}