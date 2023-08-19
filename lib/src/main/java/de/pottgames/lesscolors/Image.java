package de.pottgames.lesscolors;

import java.awt.image.BufferedImage;

public class Image {
    private final BufferedImage image;


    public Image(BufferedImage image) {
        this.image = image;
    }


    public void convertColors(ColorPalette palette) {
        for (int x = 0; x < this.image.getWidth(); x++) {
            for (int y = 0; y < this.image.getHeight(); y++) {
                int pixel = this.image.getRGB(x, y);
                Color originalColor = Color.fromArgbInt(pixel);
                Color newColor = palette.findClosestColor(originalColor);
                this.image.setRGB(x, y, newColor.toArgbInt());
            }
        }
    }


    public BufferedImage getImage() {
        return this.image;
    }

}
