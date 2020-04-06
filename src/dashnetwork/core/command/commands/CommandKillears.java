package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandKillears extends CoreCommand {

    public CommandKillears() {
        super("killears", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 0) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets.addAll(targets);
        }

        for (Player target : targets)
            if (!SenderUtils.canSee(sender, target))
                targets.remove(target);

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player>");
        else {
            for (Player target : targets) {
                MessageUtils.message(target, "&c&lRIP YOUR EARS");

                for (int i = 0; i < 3; i++)
                    for (Sound sound : Sound.values())
                        target.playSound(target.getLocation(), sound, 10.0F, 1.0F);
            }

            String displaynames = ListUtils.fromList(ListUtils.toDisplayNames(targets), false ,false);
            String names = ListUtils.fromList(ListUtils.toNames(targets), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Killearsed ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
