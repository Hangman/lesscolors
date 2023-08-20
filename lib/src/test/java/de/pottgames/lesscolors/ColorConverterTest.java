package de.pottgames.lesscolors;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorConverterTest {
    private static final float FLOAT_TOLERANCE = 0.01f;


    @ParameterizedTest
    @CsvSource({"0, 0, 0, 0.0, 0.0, 0.0", "255, 255, 255, 100.0, 0.0, 0.0",
            "178, 123, 99, 56.616305930208014, 18.479678048566207, 21.724036128417403",
            "1, 254, 33, 87.48495020566422, -85.20293592176454, 79.287676693237",
            "1, 1, 1, 0.27417592423966397, 0.00003730131757639921, -0.00007380574454374234"})
    public void testRgbToLab(int r, int g, int b, float expectedL, float expectedA, float expectedB) {
        float[] lab = ColorConversionUtil.rgbToLab(r, g, b, 255);
        assertEquals(expectedL, lab[0], FLOAT_TOLERANCE);
        assertEquals(expectedA, lab[1], FLOAT_TOLERANCE);
        assertEquals(expectedB, lab[2], FLOAT_TOLERANCE);
    }


    @ParameterizedTest
    @CsvSource({"0.0, 0.0, 0.0, 0, 0, 0", "100.0, 0.0, 0.0, 255, 255, 255",
            "56.616305930208014, 18.479678048566207, 21.724036128417403, 178, 123, 99",
            "87.48495020566422, -85.20293592176454, 79.287676693237, 1, 254, 33",
            "0.27417592423966397, 0.00003730131757639921, -0.00007380574454374234, 1, 1, 1"})
    public void testLabToRgb(float l, float a, float b, float expectedR, float expectedG, float expectedB) {
        int[] rgb = ColorConversionUtil.labToRgb(l, a, b, 1f);
        assertEquals(expectedR, rgb[0]);
        assertEquals(expectedG, rgb[1]);
        assertEquals(expectedB, rgb[2]);
    }

}
