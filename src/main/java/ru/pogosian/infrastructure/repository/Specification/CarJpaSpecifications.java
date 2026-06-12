package ru.pogosian.infrastructure.repository.Specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.pogosian.business.cars.ColorTypes;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarModelJpaEntity;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
public final class CarJpaSpecifications {
    public static Specification<CarJpaEntity> byFilter(Filter.CarFilter filter) {
        if (filter == null) {
            return Specification.where(null);
        }

        return Specification.where(maxEngineVolume(filter.maxEngineVolume()))
                .and(hasColors(filter.color()))
                .and(hasModelBrands(filter.modelBrand()))
                .and(hasModelNames(filter.modelName()))
                .and(hasBodyType(filter.bodyType()))
                .and(hasFuelType(filter.fuelType()))
                .and(hasGearboxType(filter.gearboxType()))
                .and(hasDriveType(filter.driveType()))
                .and(minPrice(filter.minPrice()))
                .and(maxPrice(filter.maxPrice()))
                .and(minHorsePower(filter.minHorsePower()))
                .and(maxHorsePower(filter.maxHorsePower()))
                .and(minEngineVolume(filter.minEngineVolume()));
    }

    private static Specification<CarJpaEntity> notRemoved() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("removed"));
    }

    private static Specification<CarJpaEntity> hasColors(Set<ColorTypes> colors) {
        return (root, query, criteriaBuilder) -> {
            if (colors == null || colors.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("color").in(colors);
        };
    }

    private static Specification<CarJpaEntity> hasModelBrands(Set<String> modelBrands) {
        return (root, query, criteriaBuilder) -> {
            if (modelBrands == null || modelBrands.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.lower(modelJoin.get("modelBrand")).in(
                    modelBrands.stream()
                            .filter(s -> s != null && !s.isBlank())
                            .map(String::toLowerCase)
                            .toList()
            );
        };
    }

    private static Specification<CarJpaEntity> hasModelNames(Set<String> modelNames) {
        return (root, query, criteriaBuilder) -> {
            if (modelNames == null || modelNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.lower(modelJoin.get("modelName")).in(
                    modelNames.stream()
                            .filter(s -> s != null && !s.isBlank())
                            .map(String::toLowerCase)
                            .toList()
            );
        };
    }

    private static Specification<CarJpaEntity> hasBodyType(Object bodyType) {
        return (root, query, criteriaBuilder) -> {
            if (bodyType == null) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.equal(modelJoin.get("bodyType"), bodyType);
        };
    }

    private static Specification<CarJpaEntity> hasFuelType(Object fuelType) {
        return (root, query, criteriaBuilder) -> {
            if (fuelType == null) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.equal(modelJoin.get("fuelType"), fuelType);
        };
    }

    private static Specification<CarJpaEntity> hasGearboxType(Object gearboxType) {
        return (root, query, criteriaBuilder) -> {
            if (gearboxType == null) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.equal(modelJoin.get("gearboxType"), gearboxType);
        };
    }

    private static Specification<CarJpaEntity> hasDriveType(Object driveType) {
        return (root, query, criteriaBuilder) -> {
            if (driveType == null) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.equal(modelJoin.get("driveType"), driveType);
        };
    }

    private static Specification<CarJpaEntity> minPrice(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    private static Specification<CarJpaEntity> maxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (maxPrice == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    private static Specification<CarJpaEntity> minHorsePower(int minHorsePower) {
        return (root, query, criteriaBuilder) -> {
            if (minHorsePower <= 0) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.greaterThanOrEqualTo(modelJoin.get("horsePower"), minHorsePower);
        };
    }

    private static Specification<CarJpaEntity> maxHorsePower(int maxHorsePower) {
        return (root, query, criteriaBuilder) -> {
            if (maxHorsePower <= 0) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.lessThanOrEqualTo(modelJoin.get("horsePower"), maxHorsePower);
        };
    }

    private static Specification<CarJpaEntity> minEngineVolume(double minEngineVolume) {
        return (root, query, criteriaBuilder) -> {
            if (minEngineVolume <= 0) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.greaterThanOrEqualTo(modelJoin.get("engineVolume"), minEngineVolume);
        };
    }

    private static Specification<CarJpaEntity> maxEngineVolume(double maxEngineVolume) {
        return (root, query, criteriaBuilder) -> {
            if (maxEngineVolume <= 0) {
                return criteriaBuilder.conjunction();
            }

            var configurationJoin = root.join("configuration");
            var modelJoin = configurationJoin.join("carModel");

            return criteriaBuilder.lessThanOrEqualTo(modelJoin.get("engineVolume"), maxEngineVolume);
        };
    }
}