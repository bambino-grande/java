package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.cars.ColorTypes;

import java.awt.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cars")
@SQLRestriction("removed = false")
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

}
