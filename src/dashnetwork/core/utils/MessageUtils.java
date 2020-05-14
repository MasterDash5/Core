package dashnetwork.core.utils;

import dashnetwork.core.Core;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

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
        message(sender, "&6&l» &7Unknown command. Type &6/help&7 for help.");
    }

    public static void playerCommandOnly() {
        message(Bukkit.getConsoleSender(), "&6&l» &7Only players can use this command");
    }

    @Deprecated
    public static void notOnline(CommandSender sender, String name) {
        message(sender, "&6&l» &7Could not find player: &c" + name);
    }

    @Deprecated
    public static void broadcast(String message, boolean showConsole) {
        for (Player online : Bukkit.getOnlinePlayers())
            message(online, message);

        if (showConsole)
            message(Bukkit.getConsoleSender(), message);
    }

    @Deprecated
    public static void broadcast(String message, boolean showConsole, Player... exempt) {
        List list = exempt == null ? new ArrayList() : Arrays.asList(exempt);

        for (Player online : Bukkit.getOnlinePlayers())
            if (exempt == null || !list.contains(online))
                message(online, message);

        if (showConsole)
            message(Bukkit.getConsoleSender(), message);
    }

    @Deprecated
    public static void broadcast(String message, boolean showConsole, UUID... exempt) {
        List list = exempt == null ? new ArrayList() : Arrays.asList(exempt);

        for (Player online : Bukkit.getOnlinePlayers())
            if (exempt == null || !list.contains(online.getUniqueId()))
                message(online, message);

        if (showConsole)
            message(Bukkit.getConsoleSender(), message);
    }

    @Deprecated
    public static void broadcast(String message, World world) {
        for (Player online : world.getPlayers())
            message(online, message);
    }

    @Deprecated
    public static void broadcast(String message, World world, Player... exempt) {
        List list = exempt == null ? new ArrayList() : Arrays.asList(exempt);

        for (Player online : world.getPlayers())
            if (exempt == null || !list.contains(online))
                message(online, message);
    }

    @Deprecated
    public static void broadcast(String message, World world, UUID... exempt) {
        List list = exempt == null ? new ArrayList() : Arrays.asList(exempt);

        for (Player online : world.getPlayers())
            if (exempt == null || !list.contains(online.getUniqueId()))
                message(online, message);
    }

    @Deprecated
    public static void broadcast(String message, World world, Collection<UUID> exempt) {
        for (Player online : world.getPlayers())
            if (exempt == null || !exempt.contains(online.getUniqueId()))
                message(online, message);
    }

    @Deprecated
    public static void broadcast(String message, String worldName) {
        for (Player online : Bukkit.getWorld(worldName).getPlayers())
            message(online, message);
    }

    @Deprecated
    public static void broadcast(String message, String worldName, Player... exempt) {
        List list = exempt == null ? new ArrayList() : Arrays.asList(exempt);

        for (Player online : Bukkit.getWorld(worldName).getPlayers())
            if (exempt == null || !list.contains(online))
                message(online, message);
    }

    @Deprecated
    public static void broadcast(String message, String worldName, UUID... exempt) {
        List list = exempt == null ? new ArrayList() : Arrays.asList(exempt);

        for (Player online : Bukkit.getWorld(worldName).getPlayers())
            if (exempt == null || !list.contains(online.getUniqueId()))
                message(online, message);
    }

    @Deprecated
    public static void broadcast(String message, String worldName, Collection<UUID> exempt) {
        for (Player online : Bukkit.getWorld(worldName).getPlayers())
            if (exempt == null || !exempt.contains(online.getUniqueId()))
                message(online, message);
    }

    @Deprecated
    public static void tellStaff(String message, boolean showConsole, boolean adminOnly) {
        for (Player online : Bukkit.getOnlinePlayers())
            if (adminOnly ? PlayerUtils.isAdmin(online) : PlayerUtils.isStaff(online))
                message(online, message);

        if (showConsole)
            message(Bukkit.getConsoleSender(), message);
    }

    @Deprecated
    public static void tellStaff(String message, Collection<UUID> exclude, boolean showConsole, boolean adminOnly) {
        for (Player online : Bukkit.getOnlinePlayers())
            if (!exclude.contains(online.getUniqueId()) && (adminOnly ? PlayerUtils.isAdmin(online) : PlayerUtils.isStaff(online)))
                message(online, message);

        if (showConsole)
            message(Bukkit.getConsoleSender(), message);
    }

    @Deprecated
    public static void message(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);

        if (player != null)
            message(player, message);
    }

    @Deprecated
    public static void message(Collection<UUID> uuids, String message) {
        for (UUID uuid : uuids)
            message(uuid, message);
    }

    @Deprecated
    public static void message(UUID[] uuids, String message) {
        for (UUID uuid : uuids)
            message(uuid, message);
    }

}
