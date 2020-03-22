package dashnetwork.core.command.commands;

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
        super("clearlag", PermissionType.ADMIN);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<World> worlds = new ArrayList<>();
        List<String> removedList = new ArrayList<>();
        int removed = 0;

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

                    if (LazyUtils.anyEquals(type, EntityType.DROPPED_ITEM, EntityType.EXPERIENCE_ORB, EntityType.AREA_EFFECT_CLOUD, EntityType.BAT)) {
                        removedList.add(name);
                        entity.remove();
                        removed++;
                    }

                    if (LazyUtils.anyEquals(type, EntityType.ARROW, EntityType.SPECTRAL_ARROW) && entity.isOnGround()) {
                        removedList.add(name);
                        entity.remove();
                        removed++;
                    }

                    if (entity.fromMobSpawner()) {
                        removedList.add(name);
                        entity.remove();
                        removed++;
                    }
                }
            }
        }

        MessageBuilder builder = new MessageBuilder();
        builder.addLine("&6&l» &6" + SenderUtils.getDisplayName(sender) + " &7cleared &6" + removed + " &7entities");
        builder.addHoverEvent(HoverEvent.Action.SHOW_TEXT, "&6Removed: &7" + ListUtils.fromList(removedList, false, true));

        MessageUtils.broadcast(false, worlds, PermissionType.NONE, builder.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (SenderUtils.isAdmin(sender) && args.length > 0) {
            List<String> worlds = new ArrayList<>();

            for (World world : Bukkit.getWorlds())
                worlds.add(world.getName());

            return worlds;
        }

        return null;
    }

}
