package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.cars.ColorTypes;

import java.awt.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@Table(name = "cars")
@SQLRestriction("removed = false")
@NoArgsConstructor
public class CarJpaEntity extends BaseJpaEntity{
    @Column(unique = true,  nullable = false)
    private String carName;

    @OneToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    private CarConfigurationJpaEntity configuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColorTypes color;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean availableForSale;

    @Column(nullable = false)
    private Boolean availableForTestDrive;

    public CarJpaEntity(UUID id,String carName, CarConfigurationJpaEntity jpaEntity, ColorTypes color, BigDecimal price, Boolean availableForSale, Boolean availableForTestDrive) {
        super(id);
        this.carName = carName;
        this.configuration = jpaEntity;
        this.color = color;
        this.price = price;
        this.availableForSale = availableForSale;
    }
}
