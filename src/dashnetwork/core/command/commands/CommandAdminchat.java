package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandAdminchat extends CoreCommand {

    public CommandAdminchat() {
        super("adminchat", PermissionType.ADMIN, true);
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
                MessageUtils.usage(player, label, "<player>");
            else {
                for (Player target : targets) {
                    User user = User.getUser(target);
                    boolean inAdminChat = !user.inAdminChat();

                    user.setInAdminChat(inAdminChat);

                    if (inAdminChat)
                        MessageUtils.message(target, "&6&l» &7You are now in AdminChat");
                    else
                        MessageUtils.message(target, "&6&l» &7You are no longer in AdminChat");
                }

                if (ListUtils.containsOtherThan(targets, player)) {
                    targets.remove(player);

                    String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                    String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                    message.append("&7 toggled AdminChat");

                    MessageUtils.message(player, message.build());
                }
            }
        } else {
            if (arguments)
                MessageUtils.broadcast(true, null, PermissionType.ADMIN, "&9&lAdmin &6Console &6&l> &3" + StringUtils.unsplit(args, " "));
            else
                MessageUtils.usage(sender, label, "<message>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length <= 1 && SenderUtils.isOwner(sender))
            return null;
        return new ArrayList<>();
    }

}
