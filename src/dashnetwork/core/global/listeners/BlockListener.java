package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.User;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private Material[] blacklist = { Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.COMMAND_BLOCK_MINECART, Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.JIGSAW, Material.BARRIER };

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        Block block = event.getBlock();

        if (!WorldUtils.canBuild(user, block.getWorld()))
            event.setCancelled(true);

        if (!user.isOwner() && LazyUtils.anyEquals(block.getType(), blacklist))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        Block block = event.getBlock();

        if (!WorldUtils.canBuild(user, block.getWorld()))
            event.setCancelled(true);

        if (!user.isOwner() && LazyUtils.anyEquals(block.getType(), blacklist))
            event.setCancelled(true);
    }

}
