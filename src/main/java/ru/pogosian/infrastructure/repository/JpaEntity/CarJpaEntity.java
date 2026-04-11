package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class CarJpaEntity extends BaseJpaEntity{
    @Column(unique = true,  nullable = false)
    private String carName;

    @OneToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    private CarConfigurationJpaEntity configuration;

    @Column(nullable = false)
    @Convert(converter = ColorConverter.class)
    private Color color;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean availableForSale;

    @Column(nullable = false)
    private Boolean availableForTestDrive;

}
