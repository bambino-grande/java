package ru.pogosian.infrastructure.repository.JpaEntity.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.infrastructure.repository.JpaEntity.BaseJpaEntity;

import java.util.UUID;

@Getter
@Entity
@Table(name = "users")
@SQLRestriction("removed = false")
public class UserJpaEntity extends BaseJpaEntity {
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    public UserJpaEntity(UUID id, String name, UserType userType) {
        super(id);
        this.name = name;
        this.type = userType;
    }
}