package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandStaffchat extends CoreCommand {

    public CommandStaffchat() {
        super("staffchat", PermissionType.STAFF);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        boolean hasArgs = args.length > 0;

        if (sender instanceof Player) {
            Player target = null;

            if (hasArgs && SenderUtils.isAdmin(sender))
                target = Bukkit.getPlayer(args[0]);
            else if (sender instanceof Player)
                target = (Player) sender;

            if (target == null || !SenderUtils.canSee(sender, target))
                MessageUtils.usage(sender, label, "<player>");
            else {
                User user = User.getUser(target);
                boolean inStaffChat = !user.inStaffChat();

                user.setInStaffChat(inStaffChat);

                if (inStaffChat) {
                    MessageUtils.message(target, "&6&l» &7You are now in StaffChat");

                    if (!sender.equals(target)) {
                        MessageBuilder message = new MessageBuilder();
                        message.append("&6&l» ");
                        message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
                        message.append("&7 is now in StaffChat");
                        MessageUtils.message(sender, message.build());
                    }
                } else {
                    MessageUtils.message(target, "&6&l» &7You are no longer in StaffChat");

                    if (!sender.equals(target)) {
                        MessageBuilder message = new MessageBuilder();
                        message.append("&6&l» ");
                        message.append("&6" + target.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + target.getName());
                        message.append("&7 is no longer in StaffChat");
                        MessageUtils.message(sender, message.build());
                    }
                }
            }
        } else {
            if (hasArgs)
                MessageUtils.broadcast(true, null, PermissionType.STAFF, "&9&lStaff &6Console &6&l> &6" + StringUtils.unsplit(args, " "));
            else
                MessageUtils.usage(sender, label, "<message>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && SenderUtils.isAdmin(sender))
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
