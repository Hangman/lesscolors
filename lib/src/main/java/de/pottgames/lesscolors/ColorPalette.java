package de.pottgames.lesscolors;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a collection of colors, providing methods for color manipulation and retrieval.
 */
public class ColorPalette implements Iterable<Color> {
    private final Color[] colors;


    /**
     * Constructs a ColorPalette from an array of colors.
     *
     * @param colors An array of Color objects to populate the palette.
     */
    public ColorPalette(Color[] colors) {
        this.colors = colors;
    }


    /**
     * Constructs a ColorPalette from an image by extracting colors from its pixels.
     *
     * @param image The BufferedImage from which colors will be extracted.
     */
    public ColorPalette(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int pixels = image.getWidth() * image.getHeight();
        Color[] colors = new Color[pixels];
        int index = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = image.getRGB(x, y);
                colors[index++] = Color.fromArgbInt(pixel);
            }
        }

        this.colors = colors;
    }


    /**
     * Finds the closest color in the palette to a given color.
     *
     * @param other The color to which the closest color in the palette is sought.
     * @return The closest Color object in the palette to the given color.
     */
    public Color findClosestColor(Color other) {
        Color closestColor = null;
        float closestDistance = Float.MAX_VALUE;

        for (Color color : this.colors) {
            float distance = color.labDistance(other);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestColor = color;
            }
        }

        return closestColor;
    }


    /**
     * Provides an iterator for iterating through the colors in the palette.
     *
     * @return An iterator for iterating through the Color objects in the palette.
     */
    @Override
    public Iterator<Color> iterator() {
        return new ColorIterator();
    }


    private class ColorIterator implements Iterator<Color> {
        private int currentIndex = 0;


        @Override
        public boolean hasNext() {
            return currentIndex < colors.length;
        }


        @Override
        public Color next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Color color = colors[currentIndex];
            currentIndex++;
            return color;
        }

    }

}
