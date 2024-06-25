package artcreator.domain.port;


import java.awt.*;
import java.util.HashMap;

public class Template{
    private final Color[][] grid;
    private  HashMap<Color, Integer> colorCountList;

    public Template(Color[][] grid){
        this.grid = grid;
        countColors();
    }

    private void countColors(){
        colorCountList = new HashMap<>();

        for (Color[] colors : grid) {
            for (Color currentColor : colors) {
                if (colorCountList.containsKey(currentColor)) {
                    colorCountList.put(currentColor, colorCountList.get(currentColor) + 1);
                } else {
                    colorCountList.put(currentColor, 1);
                }
            }
        }
    }

    public Color[][] getGrid() {
        return grid;
    }

    public HashMap<Color, Integer> getColorCountList() {
        return colorCountList;
    }
}