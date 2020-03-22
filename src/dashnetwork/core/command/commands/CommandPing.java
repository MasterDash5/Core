package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.GrammarUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandPing extends CoreCommand {

    public CommandPing() {
        super("ping", PermissionType.NONE);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0)
            target = Bukkit.getPlayer(args[0]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null)
            MessageUtils.usage(sender, label, "<player>");
        else
            MessageUtils.message(sender, "&6&l» &7" + GrammarUtils.possessive(target.getName()) + " ping: &6" + target.spigot().getPing() + "ms");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            List<String> players = new ArrayList<>();

            for (Player online : Bukkit.getOnlinePlayers())
                if (SenderUtils.canSee(sender, online))
                    players.add(online.getName());

            return players;
        }

        return null;
    }

}
