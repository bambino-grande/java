package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Getter
@Entity
@Table(name = "processed_events")
@SQLRestriction("removed = false")
@NoArgsConstructor
public class ProcessedEventJpaEntity extends BaseJpaEntity {
    public  ProcessedEventJpaEntity(UUID id) {
        super(id);
    }
}
