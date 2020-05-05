package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPing extends CoreCommand {

    public CommandPing() {
        super("ping", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0)
            target = SelectorUtils.getPlayer(sender, args[0]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player>");
        else {
            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
            message.append(" &7ping: &6" + target.spigot().getPing() + "ms");

            MessageUtils.message(sender, message.build());
        }
    }

}
