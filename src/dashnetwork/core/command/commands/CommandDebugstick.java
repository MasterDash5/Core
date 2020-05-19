package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.creative.Creative;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.User;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandDebugstick extends CoreCommand {

    public CommandDebugstick() {
        super("debugstick", PermissionType.NONE, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = User.getUser(player);

            if (user.isAdmin() || player.getWorld().equals(Creative.getWorld())) {
                player.getInventory().addItem(new ItemStack(Material.DEBUG_STICK));

                MessageUtils.message(sender, "&6&l» &7You have been given a &6Debug Stick");
            } else
                MessageUtils.message(sender, "&6&l» &7This command is only allowed in &6Creative");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.build();
    }

}
