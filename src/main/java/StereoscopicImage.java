import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.*;

class StereoscopicImage {

    BufferedImage result;

    StereoscopicImage(String firstFilePath, String secondFilePath) throws IOException, URISyntaxException {
        File f = new File(firstFilePath);
        File f1 = new File(secondFilePath);
        File output = new File("output.png");
        ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
        matrix.add(new ArrayList<Double>(Arrays.asList(0.299, 0.587, 0.114)));
        matrix.add(new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0)));
        matrix.add(new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0)));

        ArrayList<ArrayList<Double>> matrix1 = new ArrayList<ArrayList<Double>>();
        matrix1.add(new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0)));
        matrix1.add(new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0)));
        matrix1.add(new ArrayList<Double>(Arrays.asList(0.299, 0.587, 0.114)));
        BufferedImage img = ImageIO.read(f);
        BufferedImage img1 = ImageIO.read(f1);

        int width = img.getWidth() < img1.getWidth() ? img.getWidth() : img1.getWidth();
        int height = img.getHeight() < img1.getHeight() ? img.getHeight() : img1.getHeight();
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; i++) {
            for (int j = 1; j < height; j++) {
                Color color = new Color(img.getRGB(i, j));
                ArrayList<Integer> rgb = new ArrayList<Integer>();
                rgb.add(color.getRed());
                rgb.add(color.getGreen());
                rgb.add(color.getBlue());

                Color color1 = new Color(img1.getRGB(i, j));
                ArrayList<Integer> rgb1 = new ArrayList<Integer>();
                rgb1.add(color1.getRed());
                rgb1.add(color1.getGreen());
                rgb1.add(color1.getBlue());
                ArrayList<Integer> newrgb = new ArrayList<Integer>();
                for (int m = 0; m < 3; m++) {
                    int avg = 0;
                    for (int n = 0; n < 3; n++) {
                        avg += matrix.get(m).get(n) * rgb.get(m) + matrix1.get(m).get(n) * rgb1.get(m);
                    }
                    newrgb.add(avg);
                }
                Color newColor = new Color(newrgb.get(0), newrgb.get(1), newrgb.get(2));
                out.setRGB(i, j, newColor.getRGB());
            }
        }
        ImageIO.write(out,"png",output);
        result = out;
    }

    public static void main(String[] a)throws Throwable{
        new UI();
    }
}
