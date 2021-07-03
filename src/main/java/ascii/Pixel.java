package ascii;

public class Pixel {
    private int red;
    private int green;
    private int blue;

    public Pixel() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getBrightness(BrightnessType type) {
        switch(type) {
            case AVERAGE:
                return (red + green + blue) / 3;
            case LIGHTNESS:
                return (getMax() + getMin()) / 2;
            case LUMINOSITY:
                return (int)(0.21 * red + 0.72 * green + 0.07 * blue);
        }

        return -1;
    }

    int getMax() {
        return Math.max(red, Math.max(green, blue));
    }

    int getMin() {
        return Math.min(red, Math.min(green, blue));
    }

    @Override
    public String toString() {
        return "(" + red + ", " + green + ", " + blue + ")";
    }
}
