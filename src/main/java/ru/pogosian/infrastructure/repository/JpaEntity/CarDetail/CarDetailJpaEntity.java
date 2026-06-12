package ru.pogosian.infrastructure.repository.JpaEntity.CarDetail;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.infrastructure.repository.JpaEntity.BaseJpaEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "car_detail")
@SQLRestriction("removed = false")
public class CarDetailJpaEntity extends BaseJpaEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal deltaPrice;

    @ElementCollection
    @CollectionTable(
            name = "car_detail_compatible_models",
            joinColumns = @JoinColumn(name = "car_detail_id")
    )
    @Column(name = "model_id", nullable = false)
    private Set<UUID> compatibleModelsIds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarDetailTypes detailTypes;

    public CarDetailJpaEntity(UUID id, String name, BigDecimal deltaPrice, Set<UUID> compatibleModelsIds, CarDetailTypes detailTypes) {
        super(id);
        this.name = name;
        this.deltaPrice = deltaPrice;
        this.compatibleModelsIds = compatibleModelsIds;
        this.detailTypes = detailTypes;
    }
}