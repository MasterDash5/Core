package dashnetwork.core.utils;

import com.github.intellectualsites.plotsquared.api.PlotAPI;
import com.github.intellectualsites.plotsquared.plot.object.PlotArea;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import dashnetwork.core.creative.Creative;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlotUtils {

    private static World creative = Creative.getWorld();
    private static PlotAPI api = new PlotAPI();

    public static List<Entity> getEntitiesInPlots() {
        List<Entity> entities = new ArrayList<>();

        for (PlotArea area : api.getPlotAreas(creative.getName())) {
            for (BlockVector2 vector : area.getRegion().getChunks()) {
                Chunk chunk = creative.getChunkAt(vector.getBlockX(), vector.getBlockZ());

                entities.addAll(Arrays.asList(chunk.getEntities()));
            }
        }

        return entities;
    }
    
    public static List<Entity> getEntitiesInPlot(Location location) {
        List<Entity> entities = new ArrayList<>();

        for (PlotArea area : api.getPlotAreas(creative.getName())) {
            for (Entity entity : creative.getEntities()) {
                CuboidRegion region = area.getRegion();
                BlockVector3 min = region.getMinimumPoint();
                BlockVector3 max = region.getMaximumPoint();
                Vector locationVector = location.toVector();
                Vector entityVector = entity.getLocation().toVector();
                Vector min2 = new Vector(min.getX(), min.getY(), min.getZ());
                Vector max2 = new Vector(max.getX(), max.getY(), max.getZ());

                if (locationVector.isInAABB(min2, max2) && entityVector.isInAABB(min2, max2))
                    entities.add(entity);
            }
        }

        return entities;
    }

}
