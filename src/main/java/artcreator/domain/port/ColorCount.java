package artcreator.domain.port;

import java.awt.Color;

public class ColorCount {
    private Color color;
    private int count;

    public ColorCount(Color color, int count) {
        this.color = color;
        this.count = count;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
