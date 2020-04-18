package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SelectorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRide extends CoreCommand {

    public CommandRide() {
        super("ride", PermissionType.OWNER, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        int length = args.length;
        List<Entity> entities = new ArrayList<>();
        Entity target = null;

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (length < 2)
                entities.add(player);

            if (length == 0)
                target = player.getTargetEntity(6);
            else if (length == 1)
                target = SelectorUtils.getEntity(sender, args[0]);
        }

        if (args.length >= 2) {
            entities = SelectorUtils.getEntities(sender, args[0]);
            target = SelectorUtils.getEntity(sender, args[1]);
        }

        if (target == null || entities == null || entities.isEmpty())
            MessageUtils.usage(sender, label, "[entity]");
        else {
            for (Entity entity : entities) {
                if (!target.equals(entity)) {
                    target.addPassenger(entity);

                    MessageUtils.message(entity, "&6&l» &7You hitched a ride on &6" + target.getName());
                } else
                    MessageUtils.message(sender, "&6&l» &7You cant ride yourself silly");
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length <= 2)
            return null;
        return new ArrayList<>();
    }

}
