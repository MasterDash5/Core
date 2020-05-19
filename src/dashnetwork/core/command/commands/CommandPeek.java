package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandPeek extends CoreCommand {

    public CommandPeek() {
        super("peek", PermissionType.STAFF, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item != null) {
                ItemMeta itemMeta = item.getItemMeta();

                if (itemMeta instanceof BlockStateMeta) {
                    BlockStateMeta stateMeta = (BlockStateMeta) itemMeta;
                    BlockState state = stateMeta.getBlockState();

                    if (state instanceof Chest) {
                        Container container = (Container) state;

                        player.openInventory(container.getInventory());

                        return;
                    }
                }
            }

            MessageUtils.message(sender, "&6&l» &7You must be holding a chest");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.build();
    }

}
