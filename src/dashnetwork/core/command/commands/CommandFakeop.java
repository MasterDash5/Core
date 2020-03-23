package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandFakeop extends CoreCommand {

    public CommandFakeop() {
        super("fakeop", PermissionType.ADMIN);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0)
            target = Bukkit.getPlayer(args[0]);

        if (target == null)
            MessageUtils.usage(sender, label, "<player>");
        else {
            String name = target.getName();

            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&l» &7Fake opped ");
            builder.append(target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);

            MessageUtils.message(sender, builder.build());
            MessageUtils.message(target, "&7&o[" + sender.getName() + ": Opped " + name + "]");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
