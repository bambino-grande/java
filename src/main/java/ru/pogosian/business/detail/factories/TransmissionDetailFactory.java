package ru.pogosian.business.detail.factories;

import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.types.Transmisson;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class TransmissionDetailFactory implements DetailFactory{
    @Override
    public CarDetails create(String name, Set<UUID> compatibleCarModelsId, BigDecimal deltaPrice){
        return new Transmisson(name, compatibleCarModelsId, deltaPrice);
    }

    @Override
    public CarDetails create(String name, Set<UUID> compatibleCarModelsId, BigDecimal deltaPrice, UUID id){
        return new Transmisson(name, compatibleCarModelsId, deltaPrice, id);
    }
}