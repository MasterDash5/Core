package dashnetwork.core.global.listeners;

import dashnetwork.core.creative.Creative;
import dashnetwork.core.skyblock.Skyblock;
import dashnetwork.core.survival.Survival;
import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangedListener implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String to = player.getLocation().getWorld().getName();
        String creativeName = Creative.getWorld().getName();
        String survivalName = Survival.getWorld().getName();
        String netherName = Survival.getNether().getName();
        String endName = Survival.getEnd().getName();
        String skyblockName = Skyblock.getWorld().getName();

        if (LazyUtils.anyEquals(to, survivalName, netherName, endName, skyblockName, "KitPvP", "Prison", "skygrid-world", "skygrid-world_nether", "skygrid-world_the_end"))
            player.setGameMode(GameMode.SURVIVAL);
        else if (to.equals(creativeName) || user.isAdmin()) {
            player.setGameMode(GameMode.CREATIVE);
            player.setAllowFlight(true);
            player.setFlying(true);
        } else
            player.setGameMode(GameMode.ADVENTURE);
    }

}
