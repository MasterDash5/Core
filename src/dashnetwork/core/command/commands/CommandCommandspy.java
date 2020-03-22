package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandCommandspy extends CoreCommand {

    public CommandCommandspy() {
        super("commandspy", PermissionType.STAFF);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = User.getUser(player);
            boolean inCommandSpy = !user.inCommandSpy();

            user.setInCommandSpy(inCommandSpy);

            if (inCommandSpy)
                MessageUtils.message(sender, "&6&l» &7You are now in CommandSpy");
            else
                MessageUtils.message(sender, "&6&l» &7You are no longer in CommandSpy");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
