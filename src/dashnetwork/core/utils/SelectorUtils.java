package dashnetwork.core.utils;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.command.CraftBlockCommandSender;
import org.bukkit.craftbukkit.v1_16_R1.command.ProxiedNativeCommandSender;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftMinecartCommand;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import java.util.ArrayList;
import java.util.List;

public class SelectorUtils {

    public static List<Entity> getEntities(CommandSender sender, String selector) {
        List<Entity> entities = new ArrayList<>();
        EntitySelector entitySelector = getSelector(selector);

        if (entitySelector != null) {
            try {
                for (net.minecraft.server.v1_16_R1.Entity entity : entitySelector.getEntities(toCommandListenerWrapper(sender)))
                    entities.add(CraftEntity.getEntity((CraftServer) Bukkit.getServer(), entity));

                return entities;
            } catch (CommandSyntaxException exception) {}
        }

        return null;
    }

    public static List<Player> getPlayers(CommandSender sender, String selector) {
        List<Player> players = new ArrayList<>();
        EntitySelector entitySelector = getSelector(selector);

        if (entitySelector != null) {
            try {
                for (EntityPlayer player : entitySelector.d(toCommandListenerWrapper(sender)))
                    players.add((Player) CraftPlayer.getEntity((CraftServer) Bukkit.getServer(), player));

                return players;
            } catch (CommandSyntaxException exception) {}
        }

        return null;
    }

    public static Entity getEntity(CommandSender sender, String selector) {
        EntitySelector entitySelector = getSelector(selector);

        if (entitySelector != null) {
            try {
                return CraftEntity.getEntity((CraftServer) Bukkit.getServer(), entitySelector.a(toCommandListenerWrapper(sender)));
            } catch (CommandSyntaxException exception) {}
        }

        return null;
    }

    public static Player getPlayer(CommandSender sender, String selector) {
        EntitySelector entitySelector = getSelector(selector);

        if (entitySelector != null) {
            try {
                return (Player) CraftPlayer.getEntity((CraftServer) Bukkit.getServer(), entitySelector.c(toCommandListenerWrapper(sender)));
            } catch (CommandSyntaxException exception) {}
        }

        return null;
    }

    public static EntitySelector getSelector(String selector) {
        try {
            return new ArgumentParserSelector(new StringReader(selector)).parse();
        } catch (CommandSyntaxException exception) {
            return null;
        }
    }

    public static CommandListenerWrapper toCommandListenerWrapper(CommandSender sender) {
        if (sender instanceof Player)
            return ((CraftPlayer) sender).getHandle().getCommandListener();
        if (sender instanceof BlockCommandSender)
            return ((CraftBlockCommandSender) sender).getWrapper();
        if (sender instanceof CommandMinecart)
            return ((CraftMinecartCommand) sender).getHandle().getCommandBlock().getWrapper();
        if (sender instanceof RemoteConsoleCommandSender)
            return ((DedicatedServer) Bukkit.getServer()).remoteControlCommandListener.getWrapper();
        if (sender instanceof ConsoleCommandSender)
            return ((DedicatedServer) Bukkit.getServer()).getServerCommandListener();
        if (sender instanceof ProxiedCommandSender)
            return ((ProxiedNativeCommandSender) sender).getHandle();

        return null;
    }


}
