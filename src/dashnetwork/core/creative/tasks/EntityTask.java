package dashnetwork.core.creative.tasks;

import dashnetwork.core.creative.Creative;
import dashnetwork.core.utils.PlotUtils;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class EntityTask extends BukkitRunnable {

    @Override
    public void run() {
        List<Entity> world = Creative.getWorld().getEntities();
        List<Entity> plot = PlotUtils.getEntitiesInPlots();

        for (Entity entity : world)
            if (!plot.contains(entity))
                entity.remove();
    }

}
