package dashnetwork.core;

import dashnetwork.core.command.commands.*;
import dashnetwork.core.creative.Creative;
import dashnetwork.core.discord.listeners.DiscordMessageListener;
import dashnetwork.core.global.Global;
import dashnetwork.core.skyblock.Skyblock;
import dashnetwork.core.survival.Survival;
import dashnetwork.core.task.Task;
import dashnetwork.core.task.tasks.SpinTask;
import dashnetwork.core.utils.DataUtils;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static Core instance;
    private static DiscordMessageListener discordMessageListener;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        DataUtils.startup();

        // Register per world
        new Global();

        new Creative();
        new Skyblock();
        new Survival();

        // Register commands
        new CommandAdminchat();
        new CommandAltSpy();
        new CommandAnvil();
        new CommandAutowelcome();
        new CommandBookspy();
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
        new CommandFoxshot();
        new CommandFuckoff();
        new CommandGamestate();
        new CommandGoogle();
        new CommandInvisibility();
        new CommandKillears();
        new CommandList();
        new CommandLogin();
        new CommandMattsarmorstands();
        new CommandModlist();
        new CommandMommy();
        new CommandMoonphase();
        new CommandNightvision();
        new CommandOplist();
        new CommandOwnerchat();
        new CommandPeek();
        new CommandPing();
        new CommandPingspy();
        new CommandPlayerinfo();
        new CommandRespawn();
        new CommandServer();
        new CommandServerinfo();
        new CommandSignspy();
        new CommandSpawn();
        new CommandSpin();
        new CommandStaffchat();
        new CommandTest();
        new CommandThefurpysong();
        new CommandVersionlist();

        // Register tasks
        new SpinTask();

        if (getServer().getPluginManager().isPluginEnabled("DiscordSRV")) {
            discordMessageListener = new DiscordMessageListener();
            DiscordSRV.api.subscribe(discordMessageListener);
        }
    }

    @Override
    public void onDisable() {
        DataUtils.save();

        if (getServer().getPluginManager().isPluginEnabled("DiscordSRV")) {
            DiscordSRV.api.unsubscribe(discordMessageListener);
            discordMessageListener = null;
        }

        for (Task task : Task.getTasks())
            task.cancel();

        instance = null;
    }

}
