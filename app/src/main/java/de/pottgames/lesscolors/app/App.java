package de.pottgames.lesscolors.app;

import com.beust.jcommander.JCommander;
import de.pottgames.lesscolors.ColorPalette;
import de.pottgames.lesscolors.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Arguments arguments = new Arguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        if (!validateArguments(arguments)) {
            return;
        }

        try {
            App.process(arguments);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.err.println("An error occurred while processing the images.");
            return;
        }

        long endTime = System.nanoTime();
        long millis = (endTime - startTime) / 1000 / 1000;
        System.out.println("Successfully finished in " + millis + " ms.");
    }


    public static void process(Arguments arguments) throws IOException {
        ColorPalette palette = App.createPaletteFromFile(arguments.paletteImagePath);
        BufferedImage bufferedImage = ImageIO.read(new File(arguments.inputPath));
        Image image = new Image(bufferedImage);
        image.convertColorsByPalette(palette);
        ImageIO.write(image.getImage(), arguments.outputImageType, new File(arguments.outputPath));
    }


    public static ColorPalette createPaletteFromFile(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        return new ColorPalette(image);
    }


    private static boolean validateArguments(Arguments arguments) {
        if (arguments.inputPath == null || arguments.inputPath.isEmpty()) {
            System.err.println("Missing input image path argument.");
            return false;
        }
        if (arguments.outputPath == null || arguments.outputPath.isEmpty()) {
            System.err.println("Missing output image path argument.");
            return false;
        }
        if (arguments.paletteImagePath == null || arguments.paletteImagePath.isEmpty()) {
            System.err.println("Missing palette image path argument.");
            return false;
        }
        if (Files.notExists(Paths.get(arguments.inputPath))) {
            System.err.println("Couldn't find file: " + arguments.inputPath);
            return false;
        }
        if (Files.notExists(Paths.get(arguments.paletteImagePath))) {
            System.err.println("Couldn't find file: " + arguments.paletteImagePath);
            return false;
        }

        return true;
    }

}
