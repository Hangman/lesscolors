package de.pottgames.lesscolors;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ColorPalette implements Iterable<Color> {
    private final Color[] colors;


    public ColorPalette(Color[] colors) {
        this.colors = colors;
    }


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
