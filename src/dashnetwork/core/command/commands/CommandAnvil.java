package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MaterialUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAnvil extends CoreCommand {

    public CommandAnvil() {
        super("anvil", PermissionType.ADMIN, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Block feet = player.getLocation().getBlock();
            Block head = player.getEyeLocation().getBlock();
            Block target = null;

            if (MaterialUtils.isAir(feet.getType()))
                target = feet;
            else if (MaterialUtils.isAir(head.getType()))
                target = head;

            if (target == null)
                MessageUtils.message(sender, "&6&l» &7You must be standing inside an air block");
            else
                target.setType(Material.ANVIL);
        } else
            MessageUtils.playerCommandOnly();
    }

}
