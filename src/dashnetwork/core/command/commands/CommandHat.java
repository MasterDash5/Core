package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandHat extends CoreCommand {

    public CommandHat() {
        super("hat", PermissionType.ADMIN, false);
    }

    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerInventory inventory = player.getInventory();
            ItemStack item = inventory.getItemInMainHand();
            ItemStack old = inventory.getHelmet();

            if (item == null)
                item = new ItemStack(Material.AIR);

            inventory.setHelmet(item);
            inventory.setItemInMainHand(old);
            player.updateInventory();

            MessageUtils.message(sender, "&6&l» &7Flippity flap, enjoy your new cap");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.build();
    }

}
