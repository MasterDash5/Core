package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class CommandRespawn extends CoreCommand {

    public CommandRespawn() {
        super("respawn", PermissionType.ADMIN);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 2)
            target = Bukkit.getPlayer(args[0]);

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player>");
        else {
            target.spigot().respawn();

            MessageUtils.message(sender, "&6&l» &7You have been forced to respawn");

            if (!sender.equals(target)) {
                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» &7Forced ");
                message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
                message.append("&7 to respawn");
                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
