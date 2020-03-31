package dashnetwork.core;

import dashnetwork.core.command.commands.*;
import dashnetwork.core.creative.Creative;
import dashnetwork.core.global.Global;
import dashnetwork.core.skyblock.Skyblock;
import dashnetwork.core.survival.Survival;
import dashnetwork.core.utils.DataUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static Core instance;

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
        new CommandAnvil();
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
        new CommandForcefly();
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
        new CommandMoonphase();
        new CommandNightvision();
        new CommandOplist();
        new CommandOwnerchat();
        new CommandPing();
        new CommandPingspy();
        new CommandPlayerinfo();
        new CommandRespawn();
        new CommandServer();
        new CommandSignspy();
        new CommandSpawn();
        new CommandSpin();
        new CommandStaffchat();
        new CommandTest();
        new CommandThefurpysong();
        new CommandVersionlist();
    }

    @Override
    public void onDisable() {
        DataUtils.save();

        instance = null;
    }

}
