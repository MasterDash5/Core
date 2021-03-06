package dashnetwork.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class ListUtils {

    public static <T>boolean isEqual(List<T> one, List<T> two) {
        if (one == null && two == null)
            return true;
        else if ((one == null && two != null) || (one != null && two == null) || (one.size() != two.size()))
            return false;

        one = new ArrayList<>(one);
        two = new ArrayList<>(two);

        one.sort(null);
        two.sort(null);

        return one.equals(two);
    }

    public static <T>boolean containsOtherThan(Collection<T> collection, T contains) {
        return collection.size() > (collection.contains(contains) ? 1 : 0);
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

    @Deprecated
    public static String fromListByUuid(List<UUID> uuids, boolean useAnd, boolean blankIfEmpty) {
        List<String> playerNames = new ArrayList<>();

        for (UUID uuid : uuids) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            if (player != null)
                playerNames.add(player.getName());
        }

        return fromList(playerNames, useAnd, false);
    }

}
