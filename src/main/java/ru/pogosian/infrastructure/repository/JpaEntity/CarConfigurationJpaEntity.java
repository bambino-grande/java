package ru.pogosian.infrastructure.repository.JpaEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "car_configuration")
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
    private Set<CarDetailJpaEntity> usedDetails;

    public CarConfigurationJpaEntity(UUID configurationId, UUID configurationModelId, BigDecimal totalPrice, Set<CarDetailJpaEntity> collect) {
        super(configurationId);
        this.configurationModelId = configurationModelId;
        this.totalPrice = totalPrice;
        this.usedDetails = collect;
    }
}