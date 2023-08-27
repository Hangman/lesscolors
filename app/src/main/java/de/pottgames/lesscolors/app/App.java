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

package de.pottgames.lesscolors.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.beust.jcommander.JCommander;

import de.pottgames.lesscolors.ColorSpace;
import de.pottgames.lesscolors.Image;
import de.pottgames.lesscolors.ImageModifier;

public class App {

    public static void main(String[] args) {
        final long startTime = System.nanoTime();

        final Arguments arguments = new Arguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        if (!App.validateArguments(arguments)) {
            return;
        }

        try {
            App.process(arguments);
        } catch (final IOException e) {
            e.printStackTrace(System.err);
            System.err.println("An error occurred while processing the images.");
            return;
        }

        final long endTime = System.nanoTime();
        final long millis = (endTime - startTime) / 1000 / 1000;
        System.out.println("Successfully finished in " + millis + " ms.");
    }


    public static void process(Arguments arguments) throws IOException {
        final ColorSpace colorSpace = ColorSpace.valueOf(arguments.colorSpace);
        final Image palette = Image.fromFilePath(arguments.paletteImagePath, colorSpace);
        final Image image = Image.fromFilePath(arguments.inputPath, colorSpace);
        final ImageModifier modifier = new ImageModifier(image);
        final File outputFile = new File(arguments.outputPath);
        modifier.reduceColorsByPalette(palette);
        modifier.getImage().saveToFile(outputFile, arguments.outputImageType);
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
        if (ColorSpace.valueOf(arguments.colorSpace) == null) {
            System.err.println("Invalid color space: " + arguments.colorSpace);
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
