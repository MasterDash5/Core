package dashnetwork.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NameUtils {

    public static String getDisplayName(CommandSender sender) {
        if (sender instanceof Player)
            return ((Player) sender).getDisplayName();
        else if (sender.equals(Bukkit.getConsoleSender()))
            return "Console";

        return sender.getName(); // this returns Entity.getCustomName() if one is present
    }

    public static List<String> toNames(List<Player> players) {
        List<String> names = new ArrayList<>();

        for (Player player : players)
            names.add(player.getName());

        return names;
    }

    public static List<String> toDisplayNames(List<Player> players) {
        List<String> names = new ArrayList<>();

        for (Player player : players)
            names.add(player.getDisplayName());

        return names;
    }

}
