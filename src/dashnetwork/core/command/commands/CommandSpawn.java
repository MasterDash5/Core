package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class CommandSpawn extends CoreCommand {

    public CommandSpawn() {
        super("spawn", PermissionType.NONE);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0 && SenderUtils.isAdmin(sender))
            target = Bukkit.getPlayer(args[0]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player>");
        else {
            World world = target.getLocation().getWorld();
            String name = world.getName();
            target.teleport(world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

            MessageUtils.message(target, "&6&l» &7Teleporting to &6" + name);

            if (!sender.equals(target)) {
                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» &7Teleported ");
                message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
                message.append("&7 to " + name);
                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
