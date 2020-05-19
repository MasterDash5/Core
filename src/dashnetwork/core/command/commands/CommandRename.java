package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandRename extends CoreCommand {

    public CommandRename() {
        super("rename", PermissionType.ADMIN, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                PlayerInventory inventory = player.getInventory();
                ItemStack item = inventory.getItemInMainHand();

                if (!MaterialUtils.isAir(item.getType())) {
                    ItemMeta meta = item.getItemMeta();

                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', StringUtils.unsplit(args, " ")));
                    item.setItemMeta(meta);
                    inventory.setItemInMainHand(item);
                    player.updateInventory();
                } else
                    MessageUtils.message(sender, "&6&l» &7That item cannot be renamed");
            } else
                MessageUtils.usage(sender, label, "<name>");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.stringTypeGreedy("name")).build();
    }

}
