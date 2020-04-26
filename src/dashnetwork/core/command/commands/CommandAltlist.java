package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandAltlist extends CoreCommand {

    public CommandAltlist() {
        super("altlist", PermissionType.ADMIN, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0) {
            String arg = args[0];

            target = SelectorUtils.getPlayer(sender, arg);
        } else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player>");
        else {
            MessageUtils.message(sender, "&6&l» &7Getting list of alts...");

            String uuid = target.getUniqueId().toString();
            List<String> accounts = DataUtils.getOfflineList().get(target.getAddress().getAddress().getHostAddress());

            if (accounts != null && ListUtils.containsOtherThan(accounts, uuid)) {
                List<String> names = new ArrayList<>();

                for (String account : accounts)
                    if (!uuid.equals(account))
                        names.add(Bukkit.getOfflinePlayer(UUID.fromString(account)).getName());

                String fromList = ListUtils.fromList(names, false, true);

                MessageUtils.message(sender, "&6&l» &7Alts: &6" + fromList);
            } else
                MessageUtils.message(sender, "&6&l» &7No alts detected");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
