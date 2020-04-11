package dashnetwork.core;

import com.comphenix.protocol.PacketType;
import dashnetwork.core.command.commands.*;
import dashnetwork.core.creative.Creative;
import dashnetwork.core.discord.listeners.DiscordMessageListener;
import dashnetwork.core.global.listeners.*;
import dashnetwork.core.packet.listeners.PacketListener;
import dashnetwork.core.survival.Survival;
import dashnetwork.core.task.Task;
import dashnetwork.core.task.tasks.ServerinfoTask;
import dashnetwork.core.task.tasks.SpinTask;
import dashnetwork.core.utils.DataUtils;
import dashnetwork.core.utils.TpsUtils;
import dashnetwork.core.utils.User;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Core extends JavaPlugin {

    private static Core instance;
    private static DiscordMessageListener discordMessageListener;
    private static PacketListener packetListener;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        packetListener = new PacketListener();
        packetListener.start();

        DataUtils.startup();
        TpsUtils.startup();

        // Register per world
        new Creative();
        new Survival();

        // Register global listeners
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new BedEnterListener(), this);
        manager.registerEvents(new BlockListener(), this);
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new DropItemListener(), this);
        manager.registerEvents(new EditBookListener(), this);
        // manager.registerEvents(new EntityMetadataListener(), this);
        manager.registerEvents(new InteractListener(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new KickListener(), this);
        manager.registerEvents(new LoginListener(), this);
        manager.registerEvents(new ParticleListener(), this);
        manager.registerEvents(new PickupItemListener(), this);
        manager.registerEvents(new PortalListener(), this);
        manager.registerEvents(new QuitListener(), this);
        manager.registerEvents(new RespawnListener(), this);
        manager.registerEvents(new ServerCommandListener(), this);
        manager.registerEvents(new ServerPingListener(), this);
        manager.registerEvents(new SignChangeListener(), this);
        manager.registerEvents(new TeleportListener(), this);
        manager.registerEvents(new WeatherListener(), this);
        manager.registerEvents(new WorldChangedListener(), this);

        // Register Discord listeners
        if (getServer().getPluginManager().isPluginEnabled("DiscordSRV")) {
            discordMessageListener = new DiscordMessageListener();
            DiscordSRV.api.subscribe(discordMessageListener);
        }

        // Register commands
        new CommandAdminchat();
        new CommandAltspy();
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
        new CommandImport();
        new CommandInvisibility();
        new CommandKillears();
        new CommandList();
        new CommandLogin();
        new CommandMattsarmorstands();
        new CommandModlist();
        new CommandMommy();
        new CommandMoonphase();
        new CommandNamemc();
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
        new CommandSkin();
        new CommandSpawn();
        new CommandSpin();
        new CommandStaffchat();
        new CommandThefurpysong();
        new CommandVanish();
        new CommandVersionlist();

        // Register tasks
        new ServerinfoTask();
        new SpinTask();

        User.getUsers();
    }

    @Override
    public void onDisable() {
        for (Task task : Task.getTasks())
            task.cancel();

        for (User user : User.getUsers())
            user.remove();

        DataUtils.save();

        if (getServer().getPluginManager().isPluginEnabled("DiscordSRV")) {
            DiscordSRV.api.unsubscribe(discordMessageListener);
            discordMessageListener = null;
        }

        packetListener.stop();
        packetListener = null;

        instance = null;
    }

    public Set<PacketType> getAllPackets() {
        return StreamSupport.stream(PacketType.values().spliterator(), false).filter(type -> type.isSupported() && type.getProtocol().equals(PacketType.Play.getProtocol())).collect(Collectors.toSet());
    }

}
