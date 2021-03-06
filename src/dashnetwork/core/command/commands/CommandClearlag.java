package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class CommandClearlag extends CoreCommand {

    public CommandClearlag() {
        super("clearlag", PermissionType.ADMIN, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<World> worlds = new ArrayList<>();
        List<String> removedList = new ArrayList<>();

        if (args.length > 0) {
            for (String arg : args) {
                World world = Bukkit.getWorld(arg);

                if (world == null)
                    MessageUtils.message(sender, "&6&l» &7\"&6" + arg + "&7\" is not a valid world");
                else
                    worlds.add(world);
            }
        } else
            worlds.addAll(Bukkit.getWorlds());

        for (World world : worlds) {
            for (Entity entity : world.getEntities()) {
                EntityType type = entity.getType();

                if (entity.getCustomName() == null) {
                    String name = entity.getName();

                    if (LazyUtils.anyEquals(type, EntityType.DROPPED_ITEM, EntityType.EXPERIENCE_ORB, EntityType.BAT)) {
                        removedList.add(name);
                        entity.remove();
                    }

                    if (LazyUtils.anyEquals(type, EntityType.ARROW, EntityType.SPECTRAL_ARROW) && (entity.isOnGround() || entity.getTicksLived() > 1200)) {
                        removedList.add(name);
                        entity.remove();
                    }
                }
            }
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ");
        message.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + sender.getName());
        message.append(" ");
        message.append("&7cleared &6" + removedList.size() + " entities").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(removedList, false, true));

        MessageUtils.broadcast(true, worlds, PermissionType.NONE, message.build());
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        for (World world : Bukkit.getWorlds())
            builder.then(Arguments.literal(world.getName()));

        return builder.build();
    }

}
