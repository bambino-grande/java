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
        if(userJpaEntity.getType() == UserType.Client)
            return new Client(userJpaEntity.getId(), userJpaEntity.getName());
        if(userJpaEntity.getType() == UserType.Manager)
            return new Manager(userJpaEntity.getId(), userJpaEntity.getName());
        if(userJpaEntity.getType() == UserType.SystemAdmin)
            return new SystemAdmin(userJpaEntity.getId(), userJpaEntity.getName());
        if(userJpaEntity.getType() == UserType.WarehouseAdmin)
            return new WarehouseAdmin(userJpaEntity.getId(), userJpaEntity.getName());
        return null;
    }

    public UserJpaEntity toJpaEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getName(),
                getUserType(user)
        );
    }
    private UserType getUserType(User user){
        if(user instanceof Client)
            return UserType.Client;
        if(user instanceof Manager)
            return UserType.Manager;
        if(user instanceof SystemAdmin)
            return UserType.SystemAdmin;
        if(user instanceof WarehouseAdmin)
            return UserType.WarehouseAdmin;
        return null;
    }
}
