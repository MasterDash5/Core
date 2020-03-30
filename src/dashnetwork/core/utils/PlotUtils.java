package dashnetwork.core.utils;

import com.github.intellectualsites.plotsquared.api.PlotAPI;
import com.github.intellectualsites.plotsquared.plot.object.PlotArea;
import com.sk89q.worldedit.math.BlockVector2;
import dashnetwork.core.creative.Creative;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlotUtils {

    public static List<Entity> getEntitiesInPlots() {
        List<Entity> entities = new ArrayList<>();
        World creative = Creative.getWorld();

        for (PlotArea area : new PlotAPI().getPlotAreas(creative.getName())) {
            for (BlockVector2 vector : area.getRegion().getChunks()) {
                Chunk chunk = creative.getChunkAt(vector.getBlockX(), vector.getBlockZ());

                entities.addAll(Arrays.asList(chunk.getEntities()));
            }
        }

        return entities;
    }

}
