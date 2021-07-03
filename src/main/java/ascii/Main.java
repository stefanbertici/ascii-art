package ascii;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //loading image file and rescaling with Scalr
        BufferedImage srcImg = null;
        BufferedImage scaledImg = null;
        try {
            int targetWidth = 100;
            int targetHeight = 100;
            srcImg = ImageIO.read(new File("small.jpg"));
            scaledImg = Scalr.resize(srcImg, Scalr.Method.BALANCED, targetWidth, targetHeight);
        } catch (IOException e) {
            System.out.println("Error loading image!");
        }

        int width = scaledImg.getWidth();
        int height = scaledImg.getHeight();
        System.out.println("Image loaded and rescaled successfully!");
        System.out.println("Image size = " + width + " x " + height);

        String asciiString = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

        char[] asciiChar = new char[70];
        for (int i = asciiString.length() - 1; i >= 0; i--) {
            asciiChar[asciiString.length() - 1 - i] = asciiString.charAt(i);
        }

        //extracting pixel RGB info and saving into a matrix
        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(scaledImg.getRGB(j, i));
                Pixel p = new Pixel(c.getRed(), c.getGreen(), c.getBlue());
                pixels[i][j] = p;
            }
        }

        //populating asciiPicture matrix with characters based on pixel RGB brightness levels
        char[][] asciiPicture = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                asciiPicture[i][j] = getChar(asciiChar, pixels[i][j].getBrightness(BrightnessType.AVERAGE));
            }
        }

        for (int i = 0; i < asciiPicture.length; i++) {
            for (int j = 0; j < asciiPicture[i].length; j++) {
                System.out.print(asciiPicture[i][j]);
            }
            System.out.println();
        }

        //printing pixel matrix RGB info
//        System.out.println("Iterating through pixel contents:");
//        for (int i = 0; i < pixels.length; i++){
//            for (int j = 0; j < pixels[0].length; j++) {
//                System.out.println(pixels[i][j].toString());
//            }
//        }

        //printing pixel matrix brightness info
//        for (int i = 0; i < pixels.length; i++){
//            for (int j = 0; j < pixels[0].length; j++) {
//                System.out.println("Average: " + pixels[i][j].getBrightness(BrightnessType.AVERAGE));
//                System.out.println("Lightness: " + pixels[i][j].getBrightness(BrightnessType.LIGHTNESS));
//                System.out.println("Luminosity: " + pixels[i][j].getBrightness(BrightnessType.LUMINOSITY));
//            }
//        }
    }

    private static char getChar(char[] asciiChar, int brightness) {
        int min = 0;
        int max = 255;
        //manual mapping of lower 7 and upper 8 levels of brightness to 5 upper and 5 lower levels of ascii
        // --> we end up with 240 levels of brightness and 60 levels of ascii still remaining
        if (brightness >= min && brightness <= 7) {
            switch (brightness) {
                case 0:
                    return asciiChar[0];
                case 1:
                    return asciiChar[0];
                case 2:
                    return asciiChar[1];
                case 3:
                    return asciiChar[1];
                case 4:
                    return asciiChar[2];
                case 5:
                    return asciiChar[3];
                case 6:
                    return asciiChar[4];
                case 7:
                    return asciiChar[5];
            }
        } else if (brightness > 247 && brightness <= max) {
            switch (brightness) {
                case 255:
                    return asciiChar[69];
                case 254:
                    return asciiChar[69];
                case 253:
                    return asciiChar[68];
                case 252:
                    return asciiChar[68];
                case 251:
                    return asciiChar[67];
                case 250:
                    return asciiChar[67];
                case 249:
                    return asciiChar[66];
                case 248:
                    return asciiChar[66];
            }
        } else {
            //we exclude the first and last 5 levels of ascii and divide up the rest evenly
            int step = brightness / 4;
            int i = 5 + step; // is the first 5 ascii characters we exclude.
            return asciiChar[i];
        }

        return 'e';
    }
}
