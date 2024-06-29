package artcreator.domain.port;

public class Profile {
    private int rotation = 0; // in Grad
    private int width = 210; // in mm
    private int length = 297; // in mm
    private int granularity = 10; // in mm
    private int markDiameter = 2; // in mm

    public Profile(){}

    public Profile(int width, int length, int granularity, int rotation, int markDiameter) {
        this.width = width;
        this.length = length;
        this.granularity = granularity;
        this.rotation = rotation;
        this.markDiameter = markDiameter;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
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
