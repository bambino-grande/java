package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.assembly.AssemblyOrderStatus;

import java.util.UUID;

@Getter
@Entity
@Table(name = "assembly_orders")
@SQLRestriction("removed = false")
@NoArgsConstructor
public class AssemblyOrderJpaEntity extends BaseJpaEntity {
    @Column(nullable = false)
    UUID sourceOrderId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AssemblyOrderStatus status;

    public AssemblyOrderJpaEntity(UUID id, UUID sourceOrderId, AssemblyOrderStatus status) {
        super(id);
        this.sourceOrderId = sourceOrderId;
        this.status = status;
    }
}