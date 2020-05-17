package dashnetwork.core.utils;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class DirectionUtils {

    public static float[] blockFaceToAngle(BlockFace face) {
        float[] angles = new float[2];
        angles[0] = 180;
        angles[1] = 0;

        if (face.equals(BlockFace.UP))
            angles[1] = -90;
        else if (face.equals(BlockFace.DOWN))
            angles[1] = 90;
        else {
            Vector start = face.getOppositeFace().getDirection();
            Vector direction = face.getDirection();

            double diffX = direction.getX() - start.getX();
            double diffZ = direction.getZ() - start.getZ();
            float angle = (float) (Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90;

            angles[0] = angle;
        }

        return angles;
    }

}
