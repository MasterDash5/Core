package dashnetwork.core.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User implements CommandSender {

    private static List<User> users = new ArrayList<>();
    private Player player;
    private List<String> mods;
    private String client;
    private boolean inCommandSpy;
    private boolean spinning;

    private User(Player player) {
        this.player = player;
        this.mods = new ArrayList<>();
        this.client = "Vanilla";
        this.inCommandSpy = false;
        this.spinning = false;

        users.add(this);
    }

    public static List<User> getUsers(boolean createNew) {
        if (createNew)
            for (Player online : Bukkit.getOnlinePlayers())
                getUser(online);
        return users;
    }

    public static User getUser(Player player) {
        for (User user : users)
            if (user.getPlayer().equals(player))
                return user;
        return new User(player);
    }

    public Player getPlayer() {
        return player;
    }

    public List<String> getMods() {
        return mods;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public boolean inCommandSpy() {
        return inCommandSpy;
    }

    public void setInCommandSpy(boolean inCommandSpy) {
        this.inCommandSpy = inCommandSpy;
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    public boolean isStaff() {
        return player.hasPermission("dashnetwork.staff") || isAdmin();
    }

    public boolean isAdmin() {
        return player.hasPermission("dashnetwork.admin") || player.isOp() || isOwner();
    }

    public boolean isOwner() {
        return player.hasPermission("dashnetwork.owner");
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(String[] messages) {
        player.sendMessage(messages);
    }

    @Override
    public void sendMessage(BaseComponent component) {
        player.sendMessage(component);
    }

    @Override
    public void sendMessage(BaseComponent... components) {
        player.sendMessage(components);
    }

    @Override
    public Server getServer() {
        return player.getServer();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public CommandSender.Spigot spigot() {
        return player.spigot();
    }

    @Override
    public boolean isPermissionSet(String permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return player.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return player.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int integer) {
        return player.addAttachment(plugin, integer);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bool) {
        return player.addAttachment(plugin, string, bool);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bool, int integer) {
        return player.addAttachment(plugin, string, bool, integer);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        player.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        player.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return player.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }

    @Override
    public void setOp(boolean op) {
        player.setOp(op);
    }

}
