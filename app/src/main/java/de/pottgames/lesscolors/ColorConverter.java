package de.pottgames.lesscolors;

import com.github.ajalt.colormath.model.LAB;
import com.github.ajalt.colormath.model.RGB;

public class ColorConverter {

    public static float[] rgbToLab(int r, int g, int b, int alpha) {
        RGB rgb = RGB.Companion.from255(r, g, b, alpha);
        LAB lab = rgb.toLAB();
        float labL = lab.getL();
        float labA = lab.getA();
        float labB = lab.getB();
        float labAlpha = lab.getAlpha();
        return new float[]{labL, labA, labB, labAlpha};
    }

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
