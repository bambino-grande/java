package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.cars.BodyType;
import ru.pogosian.business.cars.DriveType;
import ru.pogosian.business.cars.FuelType;
import ru.pogosian.business.cars.GearboxType;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;


@Getter
@Entity
@Table(name = "car_model")
@SQLRestriction("removed = false")
public class CarModelJpaEntity extends BaseJpaEntity{
    @Column (nullable = false)
    private String modelBrand;

    @Column (nullable = false)
    private String modelName;

    @Column (nullable = false)
    private BigDecimal basePrice;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column (nullable = false)
    private int horsePower;

    @Column (nullable = false)
    private double engineVolume;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private GearboxType gearboxType;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private DriveType driveType;

    @ManyToMany
    @JoinTable(
            name = "car_model_details",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    @SQLRestriction("removed = false")
    private Set<CarDetailJpaEntity> details;

    @ManyToMany
    @JoinTable(
            name = "car_model_available_details",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    @SQLRestriction("removed = false")
    private Set<CarDetailJpaEntity> availableDetails;

    public CarModelJpaEntity(UUID modelId, String modelBrand, String modelName, BodyType bodyType, Set<CarDetailJpaEntity> collect, BigDecimal basePrice, FuelType fuelType, int horsePower, double engineVolume, GearboxType gearboxType, DriveType driveType, Set<CarDetailJpaEntity> collect1) {
        super(modelId);
        this.modelBrand = modelBrand;
        this.modelName = modelName;
        this.bodyType = bodyType;
        this.basePrice = basePrice;
        this.fuelType = fuelType;
        this.horsePower = horsePower;
        this.engineVolume = engineVolume;
        this.gearboxType = gearboxType;
        this.driveType = driveType;
        this.details = collect;
        this.availableDetails = collect1;
    }
}