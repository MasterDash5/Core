package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandPingspy extends CoreCommand {

    public CommandPingspy() {
        super("pingspy", PermissionType.ADMIN);
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
            User user = User.getUser(target);
            boolean inPingSpy = !user.inPingSpy();

            user.setInPingSpy(inPingSpy);

            if (inPingSpy) {
                MessageUtils.message(target, "&6&l» &7You are now in PingSpy");

                if (!sender.equals(target)) {
                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
                    message.append("&7 is now in PingSpy");
                    MessageUtils.message(sender, message.build());
                }
            } else {
                MessageUtils.message(target, "&6&l» &7You are no longer in PingSpy");

                if (!sender.equals(target)) {
                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
                    message.append("&7 is no longer in PingSpy");
                    MessageUtils.message(sender, message.build());
                }
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && SenderUtils.isAdmin(sender))
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
