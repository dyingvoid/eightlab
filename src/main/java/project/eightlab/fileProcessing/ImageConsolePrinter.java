package project.eightlab.fileProcessing;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ImageConsolePrinter {
    public void ConvertToAscii(File imageFile) throws IOException {
        BufferedImage img = ImageIO.read(imageFile);
        FileWriter fwriter = new FileWriter("ascii.txt");
        PrintWriter writer = new PrintWriter(fwriter);

        for(int i = 0; i < img.getHeight(); i++){
            for(int j = 0; j < img.getWidth(); j++){
                Color color = new Color(img.getRGB(j, i));
                double value = (((color.getRed() * 0.30) + (color.getBlue() * 0.59) + (color
                        .getGreen() * 0.11)));
                writer.print(strChar(value));
                writer.flush();
                fwriter.flush();
            }
            writer.println();
            writer.flush();
            fwriter.flush();
        }
    }

    public String strChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str;
    }
}
