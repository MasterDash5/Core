package dashnetwork.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class ListUtils {

    public static <T>boolean containsOtherThan(Collection<T> collection, T contains) {
        return collection.size() > (collection.contains(contains) ? 1 : 0);
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

    public static List<String> getOnlinePlayers(CommandSender sender) {
        List<String> players = new ArrayList<>();

        for (Player online : Bukkit.getOnlinePlayers())
            if (sender == null || SenderUtils.canSee(sender, online))
                players.add(online.getName());

        return players;
    }

    public static String fromList(List<String> list, boolean useAnd, boolean formatDuplicates) {
        String string = "";

        if (formatDuplicates) {
            Map<String, Integer> duplicates = new HashMap<>();
            List<String> formatted = new ArrayList<>();

            for (String entry : list)
                duplicates.put(entry, duplicates.getOrDefault(entry, 0) + 1);

            for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
                int amount = entry.getValue();

                formatted.add(entry.getKey() + (amount > 1 ? " x" + amount : ""));
            }

            list = formatted;
        }

        for (int i = 0; i < list.size(); i++) {
            String entry = list.get(i);

            if (i == 0)
                string = entry;
            else if (list.size() == i + 1 && useAnd) {
                if (list.size() == 2)
                    string += " and " + entry;
                else
                    string += ", and " + entry;
            } else
                string += ", " + entry;
        }

        if (string.isEmpty())
            return "None";

        return string;
    }

}
