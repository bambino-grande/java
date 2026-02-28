package ru.pogosian.business.detail.factories;

import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.types.Wheel;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class WheelDetailsFactory implements DetailFactory{
    @Override
    public CarDetails create(String name, Set<UUID> id, BigDecimal deltaPrice){
        return new Wheel(name, id, deltaPrice);
    }
}
