package de.pottgames.lesscolors.app;

import com.beust.jcommander.Parameter;

public class Arguments {
    @Parameter(names = {"-input", "--input"}, description = "Path to the input image")
    public String inputImagePath;

    @Parameter(names = {"-output", "--output"}, description = "The output image path")
    public String outputImagePath;

    @Parameter(names = {"-output-type", "--output-type"}, description = "File format of the output image")
    public String outputImageType = "png";

    @Parameter(names = {"-palette", "--palette", "-lut", "--lut"}, description = "Path to the color palette image")
    public String paletteImagePath;

}
