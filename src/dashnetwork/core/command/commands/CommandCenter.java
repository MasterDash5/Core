package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.DirectionUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandCenter extends CoreCommand {

    public CommandCenter() {
        super("center", PermissionType.STAFF);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            BlockFace face = BlockFace.SOUTH;

            if (args.length > 0)
                face = BlockFace.valueOf(StringUtils.unsplit(args, "_").toUpperCase());

            float[] direction = DirectionUtils.blockFaceToAngle(face);
            Location location = player.getLocation().clone();
            location.setX(location.getBlockX() + 0.5);
            location.setZ(location.getBlockZ() + 0.5);

            if (!player.isOnGround())
                location.setY(Math.round(location.getY()));

            location.setYaw(direction[0]);
            location.setPitch(direction[1]);

            player.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            List<String> faces = new ArrayList<>();

            for (BlockFace face : BlockFace.values())
                faces.add(face.name().toLowerCase());

            return faces;
        }

        return null;
    }

}
