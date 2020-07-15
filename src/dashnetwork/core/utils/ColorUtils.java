package dashnetwork.core.utils;

import org.bukkit.ChatColor;

@Deprecated
public class ColorUtils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String strip(String string) {
        return ChatColor.stripColor(color(string));
    }

}
