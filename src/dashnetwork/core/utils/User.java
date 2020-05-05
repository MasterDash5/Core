package dashnetwork.core.utils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.earth2me.essentials.UserData;
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
import java.util.concurrent.CopyOnWriteArrayList;

public class User implements CommandSender {

    private static List<User> users = new CopyOnWriteArrayList<>();
    private Player player;
    private List<UserAddon> addons;
    private List<String> mods;
    private String client;
    private boolean inOwnerChat;
    private boolean inAdminChat;
    private boolean inStaffChat;
    private boolean inCommandSpy;
    private boolean inSignSpy;
    private boolean inBookSpy;
    private boolean inAltSpy;
    private boolean inPingSpy;
    private boolean inAutoWelcome;
    private boolean inServerInfo;
    private boolean spinning;
    private boolean vanished;
    private boolean blocking;
    private boolean locked;

    private User(Player player) {
        this.player = player;
        this.addons = new CopyOnWriteArrayList<>();
        this.mods = new ArrayList<>();
        this.client = "Vanilla";
        this.inOwnerChat = false;
        this.inAdminChat = false;
        this.inStaffChat = false;
        this.inCommandSpy = false;
        this.inSignSpy = false;
        this.inBookSpy = false;
        this.inAltSpy = false;
        this.inPingSpy = false;
        this.inAutoWelcome = false;
        this.inServerInfo = false;
        this.spinning = false;
        this.vanished = false;
        this.blocking = false;
        this.locked = false;

        loadSaves();

        users.add(this);
    }

    public static List<User> getUsers() {
        for (Player online : Bukkit.getOnlinePlayers())
            if (!online.hasMetadata("NPC"))
                getUser(online);
        return users;
    }

    public static User getUser(Player player) {
        for (User user : users)
            if (user.getPlayer().equals(player))
                return user;
        return new User(player);
    }

    public <T>UserAddon getAddon(T addonClass) {
        for (UserAddon addon : addons)
            if (addon.getClass() == addonClass)
                return addon;
        return null;
    }

    public void remove() {
        String uuid = player.getUniqueId().toString();
        List<String> ownerchatList = DataUtils.getOwnerchatList();
        List<String> adminchatList = DataUtils.getAdminchatList();
        List<String> staffchatList = DataUtils.getStaffchatList();
        List<String> commandspyList = DataUtils.getCommandspyList();
        List<String> signspyList = DataUtils.getSignspyList();
        List<String> bookspyList = DataUtils.getBookspyList();
        List<String> altspyList = DataUtils.getAltspyList();
        List<String> pingspyList = DataUtils.getPingspyList();
        boolean inOwnerChatList = ownerchatList.contains(uuid);
        boolean inAdminChatList = adminchatList.contains(uuid);
        boolean inStaffChatList = staffchatList.contains(uuid);
        boolean inCommandSpyList = commandspyList.contains(uuid);
        boolean inSignSpyList = signspyList.contains(uuid);
        boolean inBookSpyList = bookspyList.contains(uuid);
        boolean inAltSpyList = altspyList.contains(uuid);
        boolean inPingSpyList = pingspyList.contains(uuid);

        if (inOwnerChat && !inOwnerChatList)
            ownerchatList.add(uuid);
        else if (!inOwnerChat && inOwnerChatList)
            ownerchatList.remove(uuid);

        if (inAdminChat && !inAdminChatList)
            adminchatList.add(uuid);
        else if (!inAdminChat && inAdminChatList)
            adminchatList.remove(uuid);

        if (inStaffChat && !inStaffChatList)
            staffchatList.add(uuid);
        else if (!inStaffChat && inStaffChatList)
            staffchatList.remove(uuid);

        if (inCommandSpy && !inCommandSpyList)
            commandspyList.add(uuid);
        else if (!inCommandSpy && inCommandSpyList)
            commandspyList.remove(uuid);

        if (inSignSpy && !inSignSpyList)
            signspyList.add(uuid);
        else if (!inSignSpy && inSignSpyList)
            signspyList.remove(uuid);

        if (inBookSpy && !inBookSpyList)
            bookspyList.add(uuid);
        else if (!inBookSpy && inBookSpyList)
            bookspyList.remove(uuid);

        if (inAltSpy && !inAltSpyList)
            altspyList.add(uuid);
        else if (!inAltSpy && inAltSpyList)
            altspyList.remove(uuid);

        if (inPingSpy && !inPingSpyList)
            pingspyList.add(uuid);
        else if (!inPingSpy && inPingSpyList)
            pingspyList.remove(uuid);

        users.remove(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void sendPacket(PacketContainer packet) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addAddon(UserAddon addon) {
        this.addons.add(addon);
    }

    public <T>void removeAddon(T clazz) {
        for (UserAddon addon : addons)
            if (addon.getClass().equals(clazz))
                addons.remove(addon);
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

    public boolean inOwnerChat() {
        return inOwnerChat;
    }

    public void setInOwnerChat(boolean inOwnerChat) {
        this.inOwnerChat = inOwnerChat;
    }

    public boolean inAdminChat() {
        return inAdminChat;
    }

    public void setInAdminChat(boolean inAdminChat) {
        this.inAdminChat = inAdminChat;
    }

    public boolean inStaffChat() {
        return inStaffChat;
    }

    public void setInStaffChat(boolean inStaffChat) {
        this.inStaffChat = inStaffChat;
    }

    public boolean inCommandSpy() {
        return inCommandSpy;
    }

    public void setInCommandSpy(boolean inCommandSpy) {
        this.inCommandSpy = inCommandSpy;
    }

    public boolean inSignSpy() {
        return this.inSignSpy;
    }

    public void setInSignSpy(boolean inSignSpy) {
        this.inSignSpy = inSignSpy;
    }

    public boolean inBookSpy() {
        return inBookSpy;
    }

    public void setInBookSpy(boolean inBookSpy) {
        this.inBookSpy = inBookSpy;
    }

    public boolean inAltSpy() {
        return inAltSpy;
    }

    public void setInAltSpy(boolean inAltSpy) {
        this.inAltSpy = inAltSpy;
    }

    public boolean inPingSpy() {
        return inPingSpy;
    }

    public void setInPingSpy(boolean inPingSpy) {
        this.inPingSpy = inPingSpy;
    }

    public boolean inAutoWelcome() {
        return inAutoWelcome;
    }

    public void setInAutoWelcome(boolean inAutoWelcome) {
        this.inAutoWelcome = inAutoWelcome;
    }

    public boolean inServerInfo() {
        return inServerInfo;
    }

    public void setInServerInfo(boolean inServerInfo) {
        this.inServerInfo = inServerInfo;
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isStaff() {
        return player.hasPermission("dashnetwork.staff") || isAdmin();
    }

    public boolean isAdmin() {
        return player.hasPermission("dashnetwork.admin") || player.isOp() || isOwner();
    }

    public boolean isOwner() {
        return player.hasPermission("dashnetwork.owner") || isDash();
    }

    public boolean isDash() {
        return LazyUtils.anyEquals(player.getUniqueId().toString(), "4f771152-ce61-4d6f-9541-1d2d9e725d0e", "d1e65ac2-5815-42fd-a900-51f520d286b2", "1dadf63d-c067-43ef-a49f-8428e3cecc78");
    }

    public boolean isMatt() {
        return LazyUtils.anyEquals(player.getUniqueId().toString(), "0e9c49ee-ed25-462f-b7c4-48cd98a30a62", "d1095122-b0d0-4a24-95e6-cac26439372d");
    }

    public boolean isGolden() {
        return player.getUniqueId().toString().equals("bbeb983a-3111-4722-bcf0-e6aafbd5f7d2");
    }

    private void loadSaves() {
        String uuid = player.getUniqueId().toString();

        if (DataUtils.getOwnerchatList().contains(uuid))
            inOwnerChat = true;

        if (DataUtils.getAdminchatList().contains(uuid))
            inAdminChat = true;

        if (DataUtils.getStaffchatList().contains(uuid))
            inStaffChat = true;

        if (DataUtils.getCommandspyList().contains(uuid))
            inCommandSpy = true;

        if (DataUtils.getSignspyList().contains(uuid))
            inSignSpy = true;

        if (DataUtils.getBookspyList().contains(uuid))
            inBookSpy = true;

        if (DataUtils.getAltspyList().contains(uuid))
            inAltSpy = true;

        if (DataUtils.getPingspyList().contains(uuid))
            inPingSpy = true;
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
