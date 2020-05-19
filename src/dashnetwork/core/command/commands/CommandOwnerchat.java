package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandOwnerchat extends CoreCommand {

    public CommandOwnerchat() {
        super("ownerchat", PermissionType.OWNER, true);
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

            for (Player target : targets)
                if (!player.canSee(target))
                    targets.remove(target);

            if (targets.isEmpty())
                MessageUtils.usage(player, label, "<player>");
            else {
                for (Player target : targets) {
                    User user = User.getUser(target);
                    boolean inOwnerChat = !user.inOwnerChat();

                    user.setInOwnerChat(inOwnerChat);

                    if (inOwnerChat)
                        MessageUtils.message(target, "&6&l» &7You are now in OwnerChat");
                    else
                        MessageUtils.message(target, "&6&l» &7You are no longer in OwnerChat");
                }

                if (ListUtils.containsOtherThan(targets, player)) {
                    targets.remove(player);

                    String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                    String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                    message.append("&7 toggled OwnerChat");

                    MessageUtils.message(player, message.build());
                }
            }
        } else {
            if (arguments)
                MessageUtils.broadcast(true, null, PermissionType.ADMIN, "&9&lOwner &6Console &6&l> &c" + StringUtils.unsplit(args, " "));
            else
                MessageUtils.usage(sender, label, "<message>");
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.playersType("targets")).build();
    }

}
