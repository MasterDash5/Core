package dashnetwork.core.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageUtils {

    private static boolean consoleColors = false;

    public static void message(CommandSender sender, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);

        if (!consoleColors && sender.equals(Bukkit.getConsoleSender()))
            message = ChatColor.stripColor(message);

        sender.sendMessage(message);
    }

    public static void message(CommandSender sender, BaseComponent[] components) {
        sender.sendMessage(components);
    }

    public static void broadcast(boolean showConsole, Collection<World> worlds, PermissionType permission, String message) {
        List<Player> players = new ArrayList<>();

        if (worlds != null)
            for (World world : worlds)
                players.addAll(world.getPlayers());
        else
            players.addAll(Bukkit.getOnlinePlayers());

        for (Player player : players)
            if (permission.hasPermission(player))
                message(player, message);

        if (showConsole)
            message(Bukkit.getConsoleSender(), message);

        players.clear();
    }

    public static void broadcast(boolean showConsole, Collection<World> worlds, PermissionType permission, BaseComponent[] components) {
        List<Player> players = new ArrayList<>();

        if (worlds != null)
            for (World world : worlds)
                players.addAll(world.getPlayers());
        else
            players.addAll(Bukkit.getOnlinePlayers());

        for (Player player : players)
            if (permission.hasPermission(player))
                message(player, components);

        if (showConsole)
            message(Bukkit.getConsoleSender(), components);

        players.clear();
    }

    public static void error(CommandSender sender, Exception exception) {
        String stacktrace = "&6" + exception.getClass().getName();

        for (StackTraceElement element : exception.getStackTrace())
            stacktrace += "\n&6at &7" + element.getClassName() + ": &6" + String.valueOf(element.getLineNumber()).replace("-1", "Unknown source");

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l» &7An error occurred... hover for more info").hoverEvent(HoverEvent.Action.SHOW_TEXT, stacktrace);

        message(sender, builder.build());
    }

    public static void usage(CommandSender sender, String label, String usage) {
        message(sender, "&6&l» &7Usage: &6/" + label + " " + usage);
    }

    public static void noPermissions(CommandSender sender) {
        message(sender, "&6&l» &7You don't have permission to do this");
    }

    public static void unknownCommand(CommandSender sender) {
        message(sender, "Unknown command. Type \"/help\" for help.");
    }

    public static void playerCommandOnly() {
        message(Bukkit.getConsoleSender(), "&6&l» &7Only players can use this command");
    }

}
