package artcreator.creator.impl;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColorMatcher {

//    public static final Color[] COLORS = {
//            new Color(0x000000), // Schwarz
//            new Color(0xFFFFFF), // Weiß
//            new Color(0xFF0000), // Rot
//            new Color(0x00FF00), // Grün
//            new Color(0x0000FF), // Blau
//            new Color(0xFFFF00), // Gelb
//            new Color(0x00FFFF), // Zyan
//            new Color(0xFF00FF), // Magenta
//            new Color(0x808080), // Grau
//            new Color(0xC0C0C0), // Hellgrau
//            new Color(0x404040), // Dunkelgrau
//            new Color(0xADD8E6), // Hellblau
//            new Color(0xFFA500), // Orange
//            new Color(0xA52A2A), // Braun
//            new Color(0x800080), // Lila
//            new Color(0xFFC0CB)  // Rosa
//    };
    public static final LinkedHashMap<Color, String> COLORS = new LinkedHashMap<>() {{
        put(new Color(0x000000), "Schwarz");
        put(new Color(0xFFFFFF), "Weiß");
        put(new Color(0xFF0000), "Rot");
        put(new Color(0x00FF00), "Grün");
        put(new Color(0x0000FF), "Blau");
        put(new Color(0xFFFF00), "Gelb");
        put(new Color(0x00FFFF), "Zyan");
        put(new Color(0xFF00FF), "Magenta");
        put(new Color(0x808080), "Grau");
        put(new Color(0xC0C0C0), "Hellgrau");
        put(new Color(0x404040), "Dunkelgrau");
        put(new Color(0xADD8E6), "Hellblau");
        put(new Color(0xFFA500), "Orange");
        put(new Color(0xA52A2A), "Braun");
        put(new Color(0x800080), "Lila");
        put(new Color(0xFFC0CB), "Rosa");
    }};


    // Berechnet die Farbdistanz zwischen zwei Farben
    public static double colorDistance(Color c1, Color c2) {
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        int b1 = c1.getBlue();
        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        int b2 = c2.getBlue();
        return Math.sqrt(Math.pow(r2 - r1, 2) + Math.pow(g2 - g1, 2) + Math.pow(b2 - b1, 2));
    }

    // Findet die nächstgelegene Farbe
    public static Color findClosestColor(Color target) {
        Color closestColor = null;
        double minDistance = Double.MAX_VALUE;
        for (Map.Entry<Color, String> color : COLORS.entrySet()) {
            double distance = colorDistance(target, color.getKey());
            if (distance < minDistance) {
                minDistance = distance;
                closestColor = color.getKey();
            }
        }

        return closestColor;
    }
}
