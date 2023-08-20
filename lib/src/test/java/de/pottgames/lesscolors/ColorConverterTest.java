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
