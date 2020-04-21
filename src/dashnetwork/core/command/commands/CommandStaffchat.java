package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandStaffchat extends CoreCommand {

    public CommandStaffchat() {
        super("staffchat", PermissionType.STAFF, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        boolean arguments = args.length > 0;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            List<Player> targets = new ArrayList<>();

            if (arguments && SenderUtils.isOwner(sender)) {
                List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

                if (selector != null)
                    targets = selector;
            } else
                targets.add(player);

            targets.removeIf(target -> !player.canSee(target));

            if (targets.isEmpty())
                MessageUtils.usage(player, label, "&6&l» &7No players found");
            else {
                for (Player target : targets) {
                    User user = User.getUser(target);
                    boolean inStaffChat = !user.inStaffChat();

                    user.setInStaffChat(inStaffChat);

                    if (inStaffChat)
                        MessageUtils.message(target, "&6&l» &7You are now in StaffChat");
                    else
                        MessageUtils.message(target, "&6&l» &7You are no longer in StaffChat");
                }

                if (ListUtils.containsOtherThan(targets, player)) {
                    targets.remove(player);

                    String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                    String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                    message.append("&7 toggled StaffChat");

                    MessageUtils.message(player, message.build());
                }
            }
        } else {
            if (arguments)
                MessageUtils.broadcast(true, null, PermissionType.STAFF, "&9&lStaff &6Console &6&l> &6" + StringUtils.unsplit(args, " "));
            else
                MessageUtils.usage(sender, label, "<message>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && SenderUtils.isAdmin(sender))
            return null;
        return new ArrayList<>();
    }

}
