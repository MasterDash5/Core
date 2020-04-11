package dashnetwork.core.task.tasks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.sun.management.OperatingSystemMXBean;
import dashnetwork.core.task.Task;
import dashnetwork.core.utils.TpsUtils;
import dashnetwork.core.utils.User;
import org.bukkit.ChatColor;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

public class ServerinfoTask extends Task {

    private static OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private static DecimalFormat formatter = new DecimalFormat("00.00");
    private static DecimalFormat memoryFormatter = new DecimalFormat("0000");

    public ServerinfoTask() {
        super(Type.REPEAT, 0, 1, true);
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();

        double serverLoad = bean.getProcessCpuLoad();
        String serverUsage = formatter.format(serverLoad * 100) + "%";

        double tps = TpsUtils.getTPS();
        String tpsFormat = formatter.format(tps);

        double allocated = runtime.totalMemory() / 1024 / 1024;
        double used = allocated - (runtime.freeMemory() / 1024 / 1024);

        String allocatedFormat = memoryFormatter.format(allocated);
        String usedFormat = memoryFormatter.format(used);

        String message = "&7TPS: &6&l" + tpsFormat + "&7 Ram: &6&l" + usedFormat + "/" + allocatedFormat + "&7 CPU: &6&l" + serverUsage;

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, EnumWrappers.TitleAction.ACTIONBAR);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', message)));
        packet.getIntegers().write(0, 0);
        packet.getIntegers().write(1, 20);
        packet.getIntegers().write(2, 20);

        try {
            for (User user : User.getUsers())
                if (user.inServerInfo())
                    ProtocolLibrary.getProtocolManager().sendServerPacket(user.getPlayer(), packet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
