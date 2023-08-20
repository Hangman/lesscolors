package de.pottgames.lesscolors;

import com.github.ajalt.colormath.model.LAB;
import com.github.ajalt.colormath.model.RGB;

/**
 * Low-level utility class for converting colors between different color spaces.
 */
public class ColorConversionUtil {

    /**
     * Converts RGB color components to LAB color space.
     *
     * @param r     The red component (0-255).
     * @param g     The green component (0-255).
     * @param b     The blue component (0-255).
     * @param alpha The alpha component (0-255).
     * @return An array of floats representing LAB color space values: {L, A, B, Alpha}.
     */
    public static float[] rgbToLab(int r, int g, int b, int alpha) {
        RGB rgb = RGB.Companion.from255(r, g, b, alpha);
        LAB lab = rgb.toLAB();
        float labL = lab.getL();
        float labA = lab.getA();
        float labB = lab.getB();
        float labAlpha = lab.getAlpha();
        return new float[]{labL, labA, labB, labAlpha};
    }


    /**
     * Converts LAB color space components to RGB color space.
     *
     * @param l     The lightness component (L).
     * @param a     The green-red component (a).
     * @param b     The blue-yellow component (b).
     * @param alpha The alpha component.
     * @return An array of integers representing RGB color space values: {Red, Green, Blue, Alpha ranging from 0-255}.
     */
    public static int[] labToRgb(float l, float a, float b, float alpha) {
        LAB lab = new LAB(l, a, b, alpha, LAB.Companion);
        RGB rgb = lab.toSRGB();
        int rgbR = rgb.getRedInt();
        int rgbG = rgb.getGreenInt();
        int rgbB = rgb.getBlueInt();
        int rgbAlpha = rgb.getAlphaInt();
        return new int[]{rgbR, rgbG, rgbB, rgbAlpha};
    }

}
