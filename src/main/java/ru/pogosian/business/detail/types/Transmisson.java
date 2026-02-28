package ru.pogosian.business.detail.types;

import ru.pogosian.business.detail.CarDetails;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class Transmisson extends CarDetails {
    public Transmisson(String name, Set<UUID> id, BigDecimal deltaPrice) {
        super(name, id, deltaPrice);
    }
}
