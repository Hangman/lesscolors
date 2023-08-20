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

import java.awt.image.BufferedImage;

/**
 * Represents an image and provides methods for color conversion.
 */
public class Image {
    private final BufferedImage image;


    /**
     * Constructs an Image object from a BufferedImage.
     *
     * @param image The BufferedImage to be wrapped by this Image object.
     */
    public Image(BufferedImage image) {
        this.image = image;
    }


    /**
     * Converts the colors in the image to the closest colors found in the provided ColorPalette.
     *
     * @param palette The ColorPalette used for color conversion.
     */
    public void convertColorsByPalette(ColorPalette palette) {
        for (int x = 0; x < this.image.getWidth(); x++) {
            for (int y = 0; y < this.image.getHeight(); y++) {
                int pixel = this.image.getRGB(x, y);
                Color originalColor = Color.fromArgbInt(pixel);
                Color newColor = palette.findClosestColor(originalColor);
                this.image.setRGB(x, y, newColor.toArgbInt());
            }
        }
    }


    /**
     * Retrieves the underlying BufferedImage.
     *
     * @return The BufferedImage wrapped by this Image object.
     */
    public BufferedImage getImage() {
        return this.image;
    }

}
