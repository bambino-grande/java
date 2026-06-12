package ru.pogosian.infrastructure.repository.JpaEntity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.infrastructure.repository.JpaEntity.BaseJpaEntity;

import java.util.UUID;

@Getter
@Entity
@Table(name = "users")
@SQLRestriction("removed = false")
@NoArgsConstructor
public class UserJpaEntity extends BaseJpaEntity {
    @Column
    private String name;

    @Column(unique = true)
    private UUID keycloakId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    public UserJpaEntity(UUID id, UUID keycloakId, String name, UserType userType) {
        super(id);
        this.keycloakId = keycloakId;
        this.name = name;
        this.type = userType;
    }
}