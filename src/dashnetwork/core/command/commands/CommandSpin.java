package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSpin extends CoreCommand {

    public CommandSpin() {
        super("spin", PermissionType.ADMIN);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0)
            target = Bukkit.getPlayer(args[0]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player>");
        else {
            User user = User.getUser(target);
            boolean spinning = !user.isSpinning();

            user.setSpinning(spinning);

            if (spinning) {
                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» &7Spin me right round baby right round").clickEvent(ClickEvent.Action.OPEN_URL, "https://youtu.be/fpmTe3TDdVU");

                MessageUtils.message(target, message.build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
