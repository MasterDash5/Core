package dashnetwork.core.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageMessage {

    private String[] lines;

    public ImageMessage(BufferedImage image, int height, char imgChar) {
        Color[][] colors = this.toChatColorArray(image, height);
        this.lines = this.toImgMessage(colors, imgChar);
    }

    public String[] getLines() {
        return this.lines;
    }

    private Color[][] toChatColorArray(BufferedImage image, int height) {
        int ratio = image.getHeight() / image.getWidth();
        int width = height / ratio;

        BufferedImage resized = resizeImage(image, width, height);
        int resizedWidth = resized.getWidth();
        int resizedHeight = resized.getHeight();
        Color[][] colors = new Color[resizedWidth][resizedHeight];

        for (int x = 0; x < resizedWidth; ++x) {
            for (int y = 0; y < resizedHeight; ++y) {
                int rgb = resized.getRGB(x, y);
                colors[x][y] = new Color(rgb, true);
            }
        }

        return colors;
    }

    private String[] toImgMessage(Color[][] colors, char character) {
        String[] lines = new String[colors[0].length];

        for (int y = 0; y < colors[0].length; ++y) {
            String line = "";

            for (int x = 0; x < colors.length; ++x) {
                String hex = ColorUtils.toHexColor(colors[x][y]);

                line += hex + character;
            }

            lines[y] = line;
        }

        return lines;
    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        AffineTransform transform = new AffineTransform();
        transform.scale((double) width / (double) image.getWidth(), (double) height / (double) image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform, 1);
        return operation.filter(image, null);
    }

}
