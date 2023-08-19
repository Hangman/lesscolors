package de.pottgames.lesscolors;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ColorPalette implements Iterable<Color> {
    private final Color[] colors;


    public ColorPalette(Color[] colors) {
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


    @NotNull
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
