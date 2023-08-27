package de.pottgames.lesscolors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ColorUnitTest {

    @Test
    public void testColorSpace() {
        Color color = Color.fromRGBA(1f, 1f, 1f, 1f);
        Assertions.assertEquals(ColorSpace.RGB, color.getColorSpace());

        color = color.toColorSpace(ColorSpace.RGB);
        Assertions.assertEquals(ColorSpace.RGB, color.getColorSpace());

        color = color.toColorSpace(ColorSpace.LAB);
        Assertions.assertEquals(ColorSpace.LAB, color.getColorSpace());

        color = color.toColorSpace(ColorSpace.OKLAB);
        Assertions.assertEquals(ColorSpace.OKLAB, color.getColorSpace());
    }

}
