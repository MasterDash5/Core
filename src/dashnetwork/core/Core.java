package dashnetwork.core;

import dashnetwork.core.command.commands.*;
import dashnetwork.core.creative.Creative;
import dashnetwork.core.global.Global;
import dashnetwork.core.skyblock.Skyblock;
import dashnetwork.core.survival.Survival;
import dashnetwork.core.tasks.SpinTask;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Register per world
        new Creative();
        new Global();
        new Skyblock();
        new Survival();

        // Register commands
        new CommandAnvil();
        new CommandCenter();
        new CommandClearchat();
        new CommandClearlag();
        new CommandColorlist();
        new CommandCommandspy();
        new CommandConsole();
        new CommandCrash();
        new CommandFakejoin();
        new CommandFakeleave();
        new CommandFakeop();
        new CommandMattsarmorstands();
        new CommandOplist();
        new CommandPing();
        new CommandSpin();
        new CommandTest();
        new CommandVersionlist();

        // Register tasks
        new SpinTask().runTaskTimer(this, 0, 1);
    }

}
