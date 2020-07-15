package dashnetwork.core.global.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.User;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private Material[] blacklist = { Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.COMMAND_BLOCK_MINECART, Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.JIGSAW };

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        Block block = event.getBlock();
        World world = block.getWorld();

        if (!WorldUtils.canBuild(user, world))
            event.setCancelled(true);

        if (!user.isOwner() && LazyUtils.anyEquals(block.getType(), blacklist))
            event.setCancelled(true);

        if (user.isLocked())
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        Block block = event.getBlock();
        World world = block.getWorld();
        BlockState state = block.getState();

        if (state instanceof Skull) {
            Skull skull = (Skull) state;
            PlayerProfile profile = skull.getPlayerProfile();

            if (profile != null && !profile.hasTextures())
                event.setCancelled(true);
        }

        if (!WorldUtils.canBuild(user, world))
            event.setCancelled(true);

        if (!user.isOwner() && LazyUtils.anyEquals(block.getType(), blacklist))
            event.setCancelled(true);

        if (user.isLocked())
            event.setCancelled(true);
    }

}
