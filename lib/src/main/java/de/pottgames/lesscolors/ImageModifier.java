package de.pottgames.lesscolors;

/**
 * The ImageModifier class provides methods for modifying an Image object.
 */
public class ImageModifier {
    private final Image image;


    /**
     * Constructs an ImageModifier with the specified Image object.
     *
     * @param image The Image object to be modified.
     */
    public ImageModifier(Image image) {
        this.image = image;
    }


    /**
     * Reduces the number of colors in the image based on a provided color palette. For each pixel in the image, it finds the closest color in the palette and
     * replaces the pixel's color with the closest color from the palette.
     *
     * @param palette The Image object representing the color palette.
     *
     * @return This ImageModifier instance after reducing colors.
     */
    public ImageModifier reduceColorsByPalette(Image palette) {
        final int width = this.image.getWidth();
        final int height = this.image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Color sourceColor = this.image.getPixel(x, y);
                final Color paletteColor = palette.findClosestColor(sourceColor);
                this.image.setPixel(paletteColor, x, y);
            }
        }

        return this;
    }


    /**
     * Gets the modified Image object.
     *
     * @return The modified Image object.
     */
    public Image getImage() {
        return this.image;
    }

}
