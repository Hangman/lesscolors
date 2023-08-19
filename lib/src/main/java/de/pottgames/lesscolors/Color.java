package de.pottgames.lesscolors;


import java.util.Objects;

public class Color {
    private final float      component1;
    private final float      component2;
    private final float      component3;
    private final float      component4;
    private final ColorSpace colorSpace;


    public Color(float component1, float component2, float component3, float component4, ColorSpace space) {
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
        this.component4 = component4;
        this.colorSpace = space;
    }


    public static Color fromRgb(float r, float g, float b, float alpha) {
        return new Color(r, g, b, alpha, ColorSpace.RGB);
    }


    public static Color fromRgbInt(int r, int g, int b, int alpha) {
        float component1 = r / 255f;
        float component2 = g / 255f;
        float component3 = b / 255f;
        float component4 = alpha / 255f;
        return new Color(component1, component2, component3, component4, ColorSpace.RGB);
    }


    public static Color fromArgbInt(int argb) {
        int a = (argb >>> 24) & 0xFF;
        int r = (argb >>> 16) & 0xFF;
        int g = (argb >>> 8) & 0xFF;
        int b = argb & 0xFF;
        return fromRgbInt(r, g, b, a);
    }


    public static Color fromLab(float l, float a, float b, float alpha) {
        return new Color(l, a, b, alpha, ColorSpace.LAB);
    }


    public Color toRgb() {
        switch (this.colorSpace) {
            case RGB:
                return this.copy();
            case LAB:
                int[] rgb = ColorConversionUtil.labToRgb(component1, component2, component3, component4);
                return Color.fromRgbInt(rgb[0], rgb[1], rgb[2], rgb[3]);
            default:
                return null;
        }
    }


    public int toArgbInt() {
        Color color = this.colorSpace == ColorSpace.RGB ? this : this.toRgb();
        int r = (int) (color.component1 * 255f);
        int g = (int) (color.component2 * 255f);
        int b = (int) (color.component3 * 255f);
        int a = (int) (color.component4 * 255f);
        assert r >= 0 && r <= 255;
        assert g >= 0 && g <= 255;
        assert b >= 0 && b <= 255;
        assert a >= 0 && a <= 255;
        return b | g << 8 | r << 16 | a << 24;
    }


    public Color toLab() {
        switch (this.colorSpace) {
            case RGB:
                return this.copy();
            case LAB:
                int[] rgb = ColorConversionUtil.labToRgb(component1, component2, component3, component4);
                return Color.fromRgbInt(rgb[0], rgb[1], rgb[2], rgb[3]);
            default:
                return null;
        }
    }


    public float labDistance(Color other) {
        Color comp1 = this.colorSpace == ColorSpace.LAB ? this : this.toLab();
        Color comp2 = other.colorSpace == ColorSpace.LAB ? other : other.toLab();
        double lSquare = Math.pow(comp1.component1 - comp2.component1, 2d);
        double aSquare = Math.pow(comp1.component2 - comp2.component2, 2d);
        double bSquare = Math.pow(comp1.component3 - comp2.component3, 2d);
        return (float) Math.sqrt(lSquare + aSquare + bSquare);
    }


    public Color copy() {
        return new Color(component1, component2, component3, component4, colorSpace);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Color color = (Color) o;
        return Float.compare(component1, color.component1) == 0 && Float.compare(component2, color.component2) == 0 &&
               Float.compare(component3, color.component3) == 0 && Float.compare(component4, color.component4) == 0 &&
               colorSpace == color.colorSpace;
    }


    @Override
    public int hashCode() {
        return Objects.hash(component1, component2, component3, component4, colorSpace);
    }


    public enum ColorSpace {
        RGB, LAB
    }

}
