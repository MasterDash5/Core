package dashnetwork.core.task.tasks;

import dashnetwork.core.creative.Creative;
import dashnetwork.core.task.Task;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class CreativeEntityTask extends Task {

    public CreativeEntityTask() {
        super(Type.REPEAT, 0, 1, true);
    }

    @Override
    public void run() {
        for (Entity entity : Creative.getWorld().getEntities())
            if (entity.getType().equals(EntityType.MINECART_COMMAND))
                entity.remove();
    }

}
