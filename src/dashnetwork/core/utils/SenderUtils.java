package dashnetwork.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SenderUtils {

    public static boolean canSee(CommandSender sender, Player player) {
        if (sender instanceof Player)
            return ((Player) sender).canSee(player);
        return true;
    }

    public static boolean isStaff(CommandSender sender) {
        if (sender instanceof Player)
            return User.getUser((Player) sender).isStaff();
        return sender.equals(Bukkit.getConsoleSender());
    }

    public static boolean isAdmin(CommandSender sender) {
        if (sender instanceof Player)
            return User.getUser((Player) sender).isAdmin();
        return sender.equals(Bukkit.getConsoleSender());
    }

    public static boolean isOwner(CommandSender sender) {
        if (sender instanceof Player)
            return User.getUser((Player) sender).isOwner();
        return sender.equals(Bukkit.getConsoleSender());
    }

}
