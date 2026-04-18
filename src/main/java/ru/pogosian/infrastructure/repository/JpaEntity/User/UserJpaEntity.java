package ru.pogosian.infrastructure.repository.JpaEntity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.infrastructure.repository.JpaEntity.BaseJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "users")
@SQLRestriction("removed = false")
public class UserJpaEntity extends BaseJpaEntity {
    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;
}