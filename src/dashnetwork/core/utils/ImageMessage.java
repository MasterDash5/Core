package dashnetwork.core.utils;

import org.bukkit.ChatColor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageMessage {

    private static Color[] colors = new Color[] {
            new Color(0, 0, 0),
            new Color(0, 0, 170),
            new Color(0, 170, 0),
            new Color(0, 170, 170),
            new Color(170, 0, 0),
            new Color(170, 0, 170),
            new Color(255, 170, 0),
            new Color(170, 170, 170),
            new Color(85, 85, 85),
            new Color(85, 85, 255),
            new Color(85, 255, 85),
            new Color(85, 255, 255),
            new Color(255, 85, 85),
            new Color(255, 85, 255),
            new Color(255, 255, 85),
            new Color(255, 255, 255) };
    private String[] lines;

    public ImageMessage(BufferedImage image, int height, char imgChar) {
        ChatColor[][] chatColors = this.toChatColorArray(image, height);
        this.lines = this.toImgMessage(chatColors, imgChar);
    }

    public String[] getLines() {
        return this.lines;
    }

    private ChatColor[][] toChatColorArray(BufferedImage image, int height) {
        int ratio = image.getHeight() / image.getWidth();
        int width = height / ratio;

        BufferedImage resized = resizeImage(image, width, height);
        int resizedWidth = resized.getWidth();
        int resizedHeight = resized.getHeight();
        ChatColor[][] colors = new ChatColor[resizedWidth][resizedHeight];

        for (int x = 0; x < resizedWidth; ++x) {
            for (int y = 0; y < resizedHeight; ++y) {
                int rgb = resized.getRGB(x, y);
                ChatColor closest = getClosestChatColor(new Color(rgb, true));

                colors[x][y] = closest;
            }
        }

        return colors;
    }

    private String[] toImgMessage(ChatColor[][] colors, char character) {
        String[] lines = new String[colors[0].length];

        for (int y = 0; y < colors[0].length; ++y) {
            String line = "";

            for (int x = 0; x < colors.length; ++x) {
                ChatColor color = colors[x][y];
                line += color != null ? colors[x][y].toString() + character : "Â§0#";
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

    private double getDistance(Color c1, Color c2) {
        double rmean = (c1.getRed() + c2.getRed()) / 2;
        double r = c1.getRed() - c2.getRed();
        double g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2 + rmean / 256;
        double weightG = 4;
        double weightB = 2 + (255 - rmean) / 256;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private boolean areIdentical(Color c1, Color c2) {
        return Math.abs(c1.getRed() - c2.getRed()) <= 5 && Math.abs(c1.getGreen() - c2.getGreen()) <= 5 && Math.abs(c1.getBlue() - c2.getBlue()) <= 5;
    }

    private ChatColor getClosestChatColor(Color color) {
        if (color.getAlpha() < 128)
            return null;
        else {
            int index = 0;
            double best = -1.0D;

            int i;

            for (i = 0; i < colors.length; i++)
                if (areIdentical(colors[i], color))
                    return ChatColor.values()[i];

            for (i = 0; i < colors.length; i++) {
                double distance = getDistance(color, colors[i]);

                if (distance < best || best == -1.0D) {
                    best = distance;
                    index = i;
                }
            }

            return ChatColor.values()[index];
        }
    }

}
