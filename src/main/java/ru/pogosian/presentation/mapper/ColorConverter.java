package ru.pogosian.presentation.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ColorConverter {
    @Named("StringToColor")
    public static Color stringToColor(String color) {
        if(color == null)
            return null;

        String[] colors = color.split(",");
        if(colors.length != 3)
            throw new IllegalArgumentException("Invalid color format");
        return new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
    }

    @Named("ColorToString")
    public  String colorToString(Color color) {
        if(color == null)
            return null;
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
}
