package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.AttributeConverter;

import java.awt.*;

public class ColorConverter implements AttributeConverter<Color, String> {
    @Override
    public String convertToDatabaseColumn(Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }

    @Override
    public Color convertToEntityAttribute(String s) {
        String[] split = s.split(",");
        return new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
}
