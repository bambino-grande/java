package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.users.*;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserType;

@Component
@AllArgsConstructor
public class UserMapper {
    public User toDomain(UserJpaEntity userJpaEntity) {
        if(userJpaEntity.getType() == UserType.USER)
            return new Client(userJpaEntity.getId(), userJpaEntity.getName(), userJpaEntity.getKeycloakId());
        if(userJpaEntity.getType() == UserType.MANAGER)
            return new Manager(userJpaEntity.getId(), userJpaEntity.getKeycloakId(), userJpaEntity.getName());
        if(userJpaEntity.getType() == UserType.ADMIN)
            return new SystemAdmin(userJpaEntity.getId(), userJpaEntity.getName(),userJpaEntity.getKeycloakId());
        if(userJpaEntity.getType() == UserType.WAREHOUSE_ADMIN)
            return new WarehouseAdmin(userJpaEntity.getId(), userJpaEntity.getName(), userJpaEntity.getKeycloakId());
        return null;
    }

    public UserJpaEntity toJpaEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getKeycloakId(),
                user.getName(),
                getUserType(user)
        );
    }

    private UserType getUserType(User user){
        if(user instanceof Client)
            return UserType.USER;
        if(user instanceof Manager)
            return UserType.MANAGER;
        if(user instanceof SystemAdmin)
            return UserType.ADMIN;
        if(user instanceof WarehouseAdmin)
            return UserType.WAREHOUSE_ADMIN;
        return null;
    }
}
