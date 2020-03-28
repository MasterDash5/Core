package dashnetwork.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SenderUtils {

    public static String getDisplayName(CommandSender sender) {
        if (sender instanceof Player)
            return ((Player) sender).getDisplayName();
        else if (sender instanceof Entity)
            return ((Entity) sender).getCustomName();
        else if (sender.equals(Bukkit.getConsoleSender()))
            return "Console";

        return sender.getName();
    }

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
