/**
 * MIT License
 *
 * Copyright (c) 2023 Matthias Finke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
