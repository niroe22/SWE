package artcreator.domain.port;

public class Profile {
    private int width = 210;
    private int height = 297;
    private int granularity = 10;
    private int rotation = 0;
    private int markDiameter = 2;

    public Profile(){}

    public Profile(int width, int height, int granularity, int rotation, int markDiameter) {
        this.width = width;
        this.height = height;
        this.granularity = granularity;
        this.rotation = rotation;
        this.markDiameter = markDiameter;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGranularity() {
        return granularity;
    }

    public int getRotation() {
        return rotation;
    }

    public int getMarkDiameter() {
        return markDiameter;
    }
}
