package ru.pogosian.business.detail.types;

import ru.pogosian.business.detail.CarDetails;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class Wheel extends CarDetails {
    public Wheel(String name, Set<UUID> id, BigDecimal deltaPrice) {
        super(name, id, deltaPrice);
    }
}
