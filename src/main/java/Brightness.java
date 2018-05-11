import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Brightness {
    private double firstImageAverageBrightness;
    private double secondImageAverageBrightness;
    BufferedImage resultFirst;
    BufferedImage resultSecond;

    Brightness(String firstPath, String secondPath) throws IOException {
        firstImageAverageBrightness = calculateAverageBrightness(firstPath);
        secondImageAverageBrightness = calculateAverageBrightness(secondPath);
        double alpha = (secondImageAverageBrightness + firstImageAverageBrightness) / 2.0;
        resultFirst = convertImage(alpha/firstImageAverageBrightness, firstPath);
        resultSecond = convertImage(alpha/secondImageAverageBrightness, secondPath);
    }

    private double calculateAverageBrightness(String image) throws IOException {
        int R = 0, G = 0, B = 0;
        File file = new File(image);
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(img.getRGB(i, j));
                R += color.getRed();
                G += color.getGreen();
                B += color.getBlue();
            }
        }
        return (R + G + B) / (width * height * 3);
    }

    private BufferedImage convertImage(double alpha, String image) throws IOException {
        int R, G, B;
        File file = new File(image);
        BufferedImage img = ImageIO.read(file);
        File output = new File("output.png");
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; i++) {
            for (int j = 1; j < height; j++) {
                Color color = new Color(img.getRGB(i, j));
                R = validateColor((int) (color.getRed() * alpha));
                G = validateColor((int) (color.getGreen() * alpha));
                B = validateColor((int) (color.getBlue() * alpha));
                out.setRGB(i, j, new Color(R, G, B).getRGB());
            }
        }
        ImageIO.write(out,"png",output);
        return out;
    }

    private int validateColor(int color) {
        if (color > 255) {
            return 255;
        } else if (color < 0) {
            return 0;
        }
        return color;
    }

}
