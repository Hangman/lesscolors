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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * The Image class represents an image with pixels in a specific color space. It allows for loading images from files, manipulating pixels, and saving images in
 * various formats.
 */
public class Image implements Iterable<Color> {
    private final int        width;
    private final int        height;
    private final ColorSpace colorSpace;
    private final Color[]    pixels;


    /**
     * Loads an image from a file path and converts it to the specified color space.
     *
     * @param path The file path of the image to load.
     * @param colorSpace The desired color space for the loaded image.
     *
     * @return An Image object representing the loaded image.
     *
     * @throws IOException If an error occurs while reading the file.
     */
    public static Image fromFilePath(String path, ColorSpace colorSpace) throws IOException {
        Objects.requireNonNull(path);

        return Image.fromFile(new File(path), colorSpace);
    }


    /**
     * Loads an image from a File object and converts it to the specified color space.
     *
     * @param file The File object representing the image file to load.
     * @param colorSpace The desired color space for the loaded image.
     *
     * @return An Image object representing the loaded image.
     *
     * @throws IOException If an error occurs while reading the file.
     * @throws IllegalArgumentException If the file does not exist.
     */
    public static Image fromFile(File file, ColorSpace colorSpace) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(colorSpace);
        if (!file.exists()) {
            throw new IllegalArgumentException("file doesn't exist: " + file.getAbsolutePath());
        }

        try (FileInputStream stream = new FileInputStream(file)) {
            final BufferedImage image = ImageIO.read(stream);
            return Image.fromBufferedImage(image, colorSpace);
        }
    }


    /**
     * Converts a BufferedImage to an Image object in the specified color space.
     *
     * @param source The source BufferedImage to convert.
     * @param colorSpace The desired color space for the converted image.
     *
     * @return An Image object representing the converted image.
     */
    public static Image fromBufferedImage(BufferedImage source, ColorSpace colorSpace) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(colorSpace);

        final int width = source.getWidth();
        final int height = source.getHeight();
        final Image image = new Image(width, height, colorSpace);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Color color = Color.fromARGBInt(source.getRGB(x, y)).toColorSpace(colorSpace);
                image.setPixel(color, x, y);
            }
        }
        return image;
    }


    /**
     * Creates an Image object with the specified width, height, and color space.
     *
     * @param width The width of the image in pixels.
     * @param height The height of the image in pixels.
     * @param colorSpace The color space of the image.
     */
    public Image(int width, int height, ColorSpace colorSpace) {
        this.width = width;
        this.height = height;
        this.colorSpace = colorSpace;
        this.pixels = new Color[width * height];
    }


    /**
     * Sets the color of a pixel at the specified coordinates (x, y).
     *
     * @param color The color to set at the specified pixel.
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     */
    public void setPixel(Color color, int x, int y) {
        this.pixels[y * this.width + x] = color.toColorSpace(this.colorSpace);
    }


    /**
     * Gets the color of the pixel at the specified coordinates (x, y).
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     *
     * @return The color of the specified pixel.
     */
    public Color getPixel(int x, int y) {
        return this.pixels[y * this.width + x];
    }


    /**
     * Finds and returns the closest color in the image to the specified color. Uses the default color space of the image.
     *
     * @param color The color for which to find the closest match.
     *
     * @return The closest color in the image.
     */
    public Color findClosestColor(Color color) {
        return this.findClosestColor(color, this.colorSpace);
    }


    /**
     * Finds and returns the closest color in the image to the specified color, considering the specified color space.
     *
     * @param color The color for which to find the closest match.
     * @param colorSpace The color space in which to compute the color distance.
     *
     * @return The closest color in the image in the specified color space.
     */
    public Color findClosestColor(Color color, ColorSpace colorSpace) {
        Objects.requireNonNull(color);

        Color closestColor = null;
        float closestDistance = Float.MAX_VALUE;
        for (final Color pixel : this.pixels) {
            final float distance = pixel.distance(color, colorSpace);
            if (distance < closestDistance) {
                closestColor = pixel;
                closestDistance = distance;
            }
        }

        return closestColor;
    }


    /**
     * Gets the width of the image in pixels.
     *
     * @return The width of the image.
     */
    public int getWidth() {
        return this.width;
    }


    /**
     * Gets the height of the image in pixels.
     *
     * @return The height of the image.
     */
    public int getHeight() {
        return this.height;
    }


    /**
     * Returns a BufferedImage with the same pixel data as this image.
     *
     * @return A BufferedImage representation of the image.
     */
    public BufferedImage toBufferedImage() {
        final BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                final int argb = this.getPixel(x, y).toArgbInt();
                bufferedImage.setRGB(x, y, argb);
            }
        }

        return bufferedImage;
    }


    /**
     * Saves the image to a file with the specified image type.
     *
     * @param file The file to which the image should be saved.
     * @param imageType The format of the image (e.g., "PNG", "JPEG").
     *
     * @throws IOException If an error occurs while saving the image.
     */
    public void saveToFile(File file, String imageType) throws IOException {
        final BufferedImage bufferedImage = this.toBufferedImage();
        ImageIO.write(bufferedImage, imageType, file);
    }


    /**
     * Provides an iterator for iterating through the colors in the image.
     *
     * @return An iterator for iterating through the Color objects in the image.
     */
    @Override
    public Iterator<Color> iterator() {
        return new ColorIterator();
    }


    private class ColorIterator implements Iterator<Color> {
        private int currentIndex = 0;


        @Override
        public boolean hasNext() {
            return this.currentIndex < Image.this.pixels.length;
        }


        @Override
        public Color next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final Color color = Image.this.pixels[this.currentIndex];
            this.currentIndex++;
            return color;
        }

    }

}
