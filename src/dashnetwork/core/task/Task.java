package dashnetwork.core.task;

import dashnetwork.core.Core;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public abstract class Task extends BukkitRunnable {

    private static List<Task> tasks = new ArrayList<>();
    protected static Core plugin = Core.getInstance();
    private Type type;
    private long delay;
    private long period;
    private boolean async;

    public Task(Type type, long delay, long period, boolean async) {
        this.type = type;
        this.delay = delay;
        this.period = period;
        this.async = async;

        start();

        tasks.add(this);
    }

    public static List<Task> getTasks() {
        return tasks;
    }

    private void start() {
        switch (type) {
            case ONCE:
                if (async)
                    runTaskLaterAsynchronously(plugin, delay);
                else
                    runTaskLater(plugin, delay);
                break;
            case REPEAT:
                if (async)
                    runTaskTimerAsynchronously(plugin, delay, period);
                else
                    runTaskTimer(plugin, delay, period);
        }
    }

    public enum Type {
        ONCE,
        REPEAT;
    }

}
