package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSignspy extends CoreCommand {

    public CommandSignspy() {
        super("signspy", PermissionType.STAFF, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length > 0 && SenderUtils.isOwner(sender)) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        } else if (player != null)
            targets.add(player);

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "&6&l» &7No players found");
        else {
            for (Player target : targets) {
                User user = User.getUser(target);
                boolean inSignSpy = !user.inSignSpy();

                user.setInSignSpy(inSignSpy);

                if (inSignSpy)
                    MessageUtils.message(target, "&6&l» &7You are now in SignSpy");
                else
                    MessageUtils.message(target, "&6&l» &7You are no longer in SignSpy");
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(ListUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(ListUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 toggled SignSpy");

                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && SenderUtils.isAdmin(sender))
            return null;
        return new ArrayList<>();
    }

}
