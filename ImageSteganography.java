/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author hi
 */
public class ImageSteganography {

    /**
     * currently only add string to .PNG only
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            // change path to image file
            String srcPath = "./image.png";
            String destPath = "./outputImage.png";

            String message = "Hello World!";

            // Encode
            BufferedImage encodedImage = encodeImage(srcPath, message);
            File outputImage = new File(destPath);

            ImageIO.write(encodedImage, "png", outputImage);

            // Decode
            String decodedMessage = decodeImage(destPath);
            
            System.out.println(decodedMessage);
        } catch (IOException e) {
            System.err.println(e.getMessage());            
        }
    }

    public static BufferedImage encodeImage(String srcPath, String message) throws IOException {
        BufferedImage image = ImageIO.read(new File(srcPath));

        int width = image.getWidth(), height = image.getHeight();
        if (message.length() > width * height) {
            System.err.println("Message too long for image");
            return null;
        }

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int r, g, b, rgb;
        int msgIndex = 0;
        int msgLength = message.length();

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                rgb = image.getRGB(x, y);

                // Load RGBA
                // a = (rgb >> 24) & 0xff;
                r = (rgb >> 16) & 0xff;
                g = (rgb >> 8) & 0xff;
                b = rgb & 0xff;

                // Modify RGB
                if (msgIndex <= msgLength) {
                    char foo = (msgIndex < msgLength) ? message.charAt(msgIndex) : 0;
                    // a = (a & 0b11111100) | ((buf >> 6) & 0b11);
                    r = (r & 0b11111100) | ((foo >> 5) & 0b11);
                    g = (g & 0b11111100) | ((foo >> 3) & 0b11);
                    b = (b & 0b11111000) | ((foo >> 0) & 0b111);
                    msgIndex++;
                }

                // Color color = new Color(r, g, b);
                output.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return output;
    }

    public static String decodeImage(String srcPath) throws IOException {
        // StringBuilder message = new StringBuilder();
        String message = "";
        BufferedImage image = ImageIO.read(new File(srcPath));
        
        int width = image.getWidth();
        int r, g, b, rgb, foo;

        for (int i = 0, n = width * image.getHeight(); i < n; ++i) {
            rgb = image.getRGB(i % width, i / width);
            r = rgb >> 16 & 0xff;
            g = rgb >> 8 & 0xff;
            b = rgb & 0xff;

            foo = ((r & 0b11) << 5) | ((g & 0b11) << 3) | (b & 0b111);

            if (foo == 0)
                break;

            // message.append((char) ch);
            message += (char) foo;
        }

        return message;
    }
}
