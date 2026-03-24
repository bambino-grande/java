package ru.pogosian.business.detail;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Set;

@Getter
public abstract class CarDetails {
    private String name;
    private UUID id =  UUID.randomUUID();
    private BigDecimal deltaPrice;
    private Set<UUID> compatibleModelsIds;

    protected CarDetails(String name, Set<UUID> compatibleModelsIds, BigDecimal deltaPrice) {
        this.name = name;
        this.compatibleModelsIds =  compatibleModelsIds;
        this.deltaPrice = deltaPrice;
    }
}