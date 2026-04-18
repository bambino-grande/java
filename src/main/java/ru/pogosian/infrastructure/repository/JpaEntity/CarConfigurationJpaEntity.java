package ru.pogosian.infrastructure.repository.JpaEntity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "car_configuration")
@SQLRestriction("removed = false")
public class CarConfigurationJpaEntity extends BaseJpaEntity{
    @Column(name = "configuration_model_id", nullable = false)
    private UUID configurationModelId;

    @ManyToOne
    @JoinColumn(name = "configuration_model_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CarModelJpaEntity carModel;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @ManyToMany
    @JoinTable(
            name = "car_configuration_used_details",
            joinColumns = @JoinColumn(name = "configuration_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    @SQLRestriction("removed = false")
    private Set<CarDetailJpaEntity> usedDetails;
}