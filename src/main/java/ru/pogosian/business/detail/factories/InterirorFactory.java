package ru.pogosian.business.detail.factories;

import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.types.Interior;
import ru.pogosian.business.detail.types.Wheel;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class InterirorFactory implements DetailFactory{
    @Override
    public CarDetails create(String name, Set<UUID> id, BigDecimal deltaPrice){
        return new Interior(name, id, deltaPrice);
    }
}


