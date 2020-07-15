package dashnetwork.core.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.*;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class NPC implements Listener {

    private static List<NPC> npcs = new ArrayList<>();
    private int entityId;
    private GameProfile profile;
    private String tabName;
    private GameMode gameMode;
    private int ping;
    private Location location;
    private boolean fire;
    private boolean sneaking;
    private boolean sprinting;
    private boolean swimming;
    private boolean invisible;
    private boolean glowing;
    private boolean gliding;

    public NPC(String username, UUID uuid) {
        this.entityId = generateEntityId();
        this.profile = new GameProfile(uuid, username);
        this.tabName = username;
        this.gameMode = GameMode.SPECTATOR;
        this.ping = 0;
        this.location = null;
        this.fire = false;
        this.sneaking = false;
        this.sprinting = false;
        this.swimming = false;
        this.invisible = false;
        this.glowing = false;
        this.gliding = false;

        npcs.add(this);
    }

    public static List<NPC> getNPCs() {
        return npcs;
    }

    public void setSkin(String texture, String signature) {
        profile.getProperties().put("textures", new Property("textures", texture, signature));
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public void setGliding(boolean gliding) {
        this.gliding = gliding;
    }

    public void sendPlayerInfo(User user, EnumWrappers.PlayerInfoAction action) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        WrappedGameProfile wrappedProfile = WrappedGameProfile.fromHandle(profile);
        PlayerInfoData data = new PlayerInfoData(wrappedProfile, ping, EnumWrappers.NativeGameMode.fromBukkit(gameMode), WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', tabName)));

        packet.getPlayerInfoAction().write(0, action);
        packet.getPlayerInfoDataLists().write(0, Arrays.asList(data));

        user.sendPacket(packet);
    }

    public void sendNamedEntitySpawn(User user) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        WrappedDataWatcher data = new WrappedDataWatcher();

        data.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), generateMetadata());

        packet.getIntegers().write(0, entityId);
        packet.getUUIDs().write(0, profile.getId());
        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());
        packet.getBytes().write(0, (byte) (location.getYaw() * 256.0F / 360.0F));
        packet.getBytes().write(1, (byte) (location.getPitch() * 256.0F / 360.0F));
        packet.getDataWatcherModifier().write(0, data);

        user.sendPacket(packet);
    }

    private int generateEntityId() {
        int count = 0;

        for (World world : Bukkit.getWorlds())
            count += world.getEntityCount();

        return ThreadLocalRandom.current().nextInt(count, Integer.MAX_VALUE);
    }

    private byte generateMetadata() {
        byte metadata = 0x00;

        if (fire)
            metadata += 0x01;
        if (sneaking)
            metadata += 0x02;
        if (sprinting)
            metadata += 0x08;
        if (swimming)
            metadata += 0x10;
        if (invisible)
            metadata += 0x20;
        if (glowing)
            metadata += 0x40;
        if (gliding)
            metadata += 0x80;

        return metadata;
    }

}
