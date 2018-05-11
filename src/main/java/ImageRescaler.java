import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageRescaler {
    public static ImageIcon rescaleImage(File source, int maxHeight, int maxWidth)  {
        int newHeight, newWidth;
        int priorHeight, priorWidth;
        BufferedImage image = null;
        ImageIcon sizeImage;

        try {
            image = ImageIO.read(source);
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Picture upload attempted & failed");
        }

        assert image != null;
        sizeImage = new ImageIcon(image);

        priorHeight = sizeImage.getIconHeight();
        priorWidth = sizeImage.getIconWidth();

        if((float)priorHeight/(float)priorWidth > (float)maxHeight/(float)maxWidth)
        {
            newHeight = maxHeight;
            newWidth = (int)(((float)priorWidth/(float)priorHeight)*(float)newHeight);
        }
        else
        {
            newWidth = maxWidth;
            newHeight = (int)(((float)priorHeight/(float)priorWidth)*(float)newWidth);
        }


        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return (new ImageIcon(resizedImg));
    }

    public static ImageIcon rescaleImage(BufferedImage image, int maxHeight, int maxWidth) {
        int newHeight, newWidth;
        int priorHeight, priorWidth;
        ImageIcon sizeImage;

        assert image != null;
        sizeImage = new ImageIcon(image);

        priorHeight = sizeImage.getIconHeight();
        priorWidth = sizeImage.getIconWidth();

        if((float)priorHeight/(float)priorWidth > (float)maxHeight/(float)maxWidth)
        {
            newHeight = maxHeight;
            newWidth = (int)(((float)priorWidth/(float)priorHeight)*(float)newHeight);
        }
        else
        {
            newWidth = maxWidth;
            newHeight = (int)(((float)priorHeight/(float)priorWidth)*(float)newWidth);
        }


        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return (new ImageIcon(resizedImg));
    }
}
