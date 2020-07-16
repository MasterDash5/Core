package dashnetwork.core.utils;

import org.bukkit.ChatColor;

import java.awt.*;

public class ColorUtils {

    @Deprecated
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Deprecated
    public static String strip(String string) {
        return ChatColor.stripColor(color(string));
    }

    public static String toHexColor(Color color) {
        String hex = String.format("%06x", color.getRGB() & 0xFFFFFF);
        String formatted = "§x";

        for (int i = 0; i < hex.length(); i++)
            formatted += "§" + hex.toCharArray()[i];

        return formatted;
    }

    public static String translateHexColors(char character, String string) {
        int length = string.length();

        if (length > 7) {
            for (int i = 0; i < length; i++) {
                char index = string.charAt(i);

                if (index == character) {
                    String hex = string.substring(i + 1, i + 8);

                    if (hex.matches("#-?[0-9a-fA-F]+")) {
                        String color = toHexColor(hexToColor(hex));

                        string = string.replace(character + hex, color);
                    }
                }
            }
        }

        return string;
    }

    public static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf( hex.substring( 1, 3 ), 16 ),
                Integer.valueOf( hex.substring( 3, 5 ), 16 ),
                Integer.valueOf( hex.substring( 5, 7 ), 16 ) );
    }

}
