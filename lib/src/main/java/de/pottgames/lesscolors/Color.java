/**
 * MIT License
 *
 * Copyright (c) 2023 Matthias Finke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.pottgames.lesscolors;

import java.util.Objects;

import com.github.ajalt.colormath.calculate.DifferenceKt;
import com.github.ajalt.colormath.model.LAB;
import com.github.ajalt.colormath.model.Oklab;
import com.github.ajalt.colormath.model.RGB;
import com.github.ajalt.colormath.model.XYZ;

/**
 * Represents a color.
 */
public class Color {
    protected final com.github.ajalt.colormath.Color color;


    /**
     * Creates a new Color object from RGBA values ranging from 0.0 to 1.0.
     *
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param alpha The alpha component.
     *
     * @return A Color object representing the specified RGBA values.
     */
    public static Color fromRGBA(float r, float g, float b, float alpha) {
        return new Color(RGB.Companion.invoke(r, g, b, alpha));
    }


    /**
     * Creates a new Color object from RGBA integers, each component has 8 bits of precision.
     *
     * @param r The red component as an integer (0-255).
     * @param g The green component as an integer (0-255).
     * @param b The blue component as an integer (0-255).
     * @param alpha The alpha component as an integer (0-255).
     *
     * @return A Color object representing the specified RGBA values.
     */
    public static Color fromRGBInts(int r, int g, int b, int alpha) {
        return new Color(RGB.Companion.from255(r, g, b, alpha));
    }


    /**
     * Creates a new Color object from an ARGB integer representation.
     *
     * @param argb The ARGB integer value.
     *
     * @return A Color object representing the specified ARGB value.
     */
    public static Color fromARGBInt(int argb) {
        final int a = argb >>> 24 & 0xFF;
        final int r = argb >>> 16 & 0xFF;
        final int g = argb >>> 8 & 0xFF;
        final int b = argb & 0xFF;
        return Color.fromRGBInts(r, g, b, a);
    }


    protected Color(com.github.ajalt.colormath.Color color) {
        this.color = color;
    }


    /**
     * Converts the color to a 32-bit ARGB integer representation.
     *
     * @return An integer value representing the color in ARGB format.
     */
    public int toArgbInt() {
        final RGB rgb = this.color.toSRGB();
        final int b = rgb.getBlueInt();
        final int g = rgb.getGreenInt();
        final int r = rgb.getRedInt();
        final int a = rgb.getAlphaInt();
        return b | g << 8 | r << 16 | a << 24;
    }


    /**
     * Returns a new Color object representing this color in the specified color space. If this color is in the desired color space, this object is returned
     * instead.
     *
     * @param colorSpace
     *
     * @return
     */
    public Color toColorSpace(ColorSpace colorSpace) {
        if (this.getColorSpace() != colorSpace) {
            switch (colorSpace) {
                case RGB:
                    return new Color(this.color.toSRGB());
                case OKLAB:
                    return new Color(this.color.toOklab());
                case LAB:
                    return new Color(this.color.toLAB());
                case XYZ:
                    return new Color(this.color.toXYZ());
            }
            throw new IllegalArgumentException("colorSpace must not be null");
        }
        return this;
    }


    /**
     * Returns the color space of this color.
     *
     * @return the color space of this color
     */
    public ColorSpace getColorSpace() {
        if (this.color instanceof RGB) {
            return ColorSpace.RGB;
        }
        if (this.color instanceof Oklab) {
            return ColorSpace.OKLAB;
        }
        if (this.color instanceof LAB) {
            return ColorSpace.LAB;
        }
        if (this.color instanceof XYZ) {
            return ColorSpace.XYZ;
        }

        return null;
    }


    /**
     * Calculates the color distance between this color and another color using the color space of this color.
     *
     * @param other The other color for which to calculate the distance.
     *
     * @return The color distance between this color and the other color.
     */
    public float distance(Color other) {
        return this.distance(other, this.getColorSpace());
    }


    /**
     * Calculates the color distance between this color and another color in the specified color space.
     *
     * @param other The other color for which to calculate the distance.
     * @param colorSpace The color space in which to compute the color distance.
     *
     * @return The color distance between this color and the other color in the specified color space.
     */
    public float distance(Color other, ColorSpace colorSpace) {
        switch (colorSpace) {
            case RGB:
                return Color.rgbDistance(this, other);
            case LAB:
                return Color.labDistance(this, other);
            case OKLAB:
                return Color.oklabDistance(this, other);
            case XYZ:
                return Color.xyzDistance(this, other);
        }
        throw new IllegalArgumentException("Invalid ColorSpace: " + colorSpace);
    }


    /**
     * Calculates the RGB color distance between two colors.
     *
     * @param color1 The first color.
     * @param color2 The second color.
     *
     * @return The RGB color distance between the two colors.
     */
    public static float rgbDistance(Color color1, Color color2) {
        Objects.requireNonNull(color1);
        Objects.requireNonNull(color2);
        return DifferenceKt.euclideanDistance(color1.color.toSRGB(), color2.color.toSRGB());
    }


    /**
     * Calculates the LAB color distance between two colors.
     *
     * @param color1 The first color.
     * @param color2 The second color.
     *
     * @return The LAB color distance between the two colors.
     */
    public static float labDistance(Color color1, Color color2) {
        Objects.requireNonNull(color1);
        Objects.requireNonNull(color2);
        return DifferenceKt.differenceCIE2000(color1.color.toLAB(), color2.color.toLAB());
    }


    /**
     * Calculates the Oklab color distance between two colors.
     *
     * @param color1 The first color.
     * @param color2 The second color.
     *
     * @return The Oklab color distance between the two colors.
     */
    public static float oklabDistance(Color color1, Color color2) {
        Objects.requireNonNull(color1);
        Objects.requireNonNull(color2);
        return DifferenceKt.euclideanDistance(color1.color.toOklab(), color2.color.toOklab());
    }


    /**
     * Calculates the XYZ color distance between two colors.
     *
     * @param color1 The first color.
     * @param color2 The second color.
     *
     * @return The XYZ color distance between the two colors.
     */
    public static float xyzDistance(Color color1, Color color2) {
        Objects.requireNonNull(color1);
        Objects.requireNonNull(color2);
        return DifferenceKt.euclideanDistance(color1.color.toXYZ(), color2.color.toXYZ());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Color otherColor = (Color) o;
        return this.color.equals(otherColor.color);
    }


    @Override
    public int hashCode() {
        return this.color.hashCode();
    }


    @Override
    public String toString() {
        return this.color.toString();
    }

}
