package dashnetwork.core.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

//Created by MM5

public class LocationUtils {
	
	public static double getDistance(Location loc1, Location loc2, boolean threeDimensional) {
		double xDif = loc1.getX() - loc2.getX();
		double yDif = loc1.getY() - loc2.getY();
		double zDif = loc1.getZ() - loc2.getZ();
		
		return threeDimensional ? Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif) : Math.sqrt(xDif * xDif + zDif * zDif);
	}
	
	public static boolean isHovering(PlayerMoveEvent event, boolean downwardsMovement, boolean surroundingBlocks) {
		Player player = event.getPlayer();

	    if (LazyUtils.anyEquals(player.getGameMode(), GameMode.CREATIVE, GameMode.SPECTATOR))
	    	return false;

	   	if (player.isInsideVehicle() || player.getVehicle() != null)
	   		return false;

	   	if (player.getAllowFlight() || player.isFlying())
	   		return false;

	   	if (player.hasPotionEffect(PotionEffectType.JUMP) && player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() > 1)
	  		return false;

	   	if (player.isSleeping() || player.isDead())
	   		return false;

	   	if (player.getLastDamageCause() != null && player.getLastDamageCause().getCause().name().contains("EXPLOSION"))
	   		return false;

	   	if (downwardsMovement && event.getFrom().getY() > event.getTo().getY())
	   	    return false;

	   	if (surroundingBlocks && hasSurroundingBlocks(player))
	   	    return false;

	   	return true;
	}
	
	public static boolean hasSurroundingBlocks(Entity entity) {
		Block at = entity.getLocation().getBlock();
	    List<Block> surroundingBlocks = getSurroundingBlocks(at);
	    
	    surroundingBlocks.addAll(getSurroundingBlocks(at.getRelative(BlockFace.UP)));
	    surroundingBlocks.addAll(getSurroundingBlocks(at.getRelative(BlockFace.DOWN)));
	    surroundingBlocks.addAll(getSurroundingBlocks(at.getRelative(BlockFace.UP).getRelative(BlockFace.UP)));
	    
	    for (Block surroundingBlock : surroundingBlocks)
	    	if (surroundingBlock.getType() != Material.AIR)
	    		return true;
	    
	    return false;
	}
	
	public static List<Block> getSurroundingBlocks(Block block) {
		List<Block> blocks = new ArrayList<>();
		
	    for (BlockFace face : BlockFace.values())
	    	blocks.add(block.getRelative(face));
	    
	    return blocks;
	}

    public static List<Block> getBlocksAt(Player player, int add) {
        List<Block> above = new ArrayList();
        Block aboveBlock = add > 0 ? player.getLocation().add(0.0D, (double)add, 0.0D).getBlock() : player.getLocation().subtract(0.0D, (double)Math.abs(add), 0.0D).getBlock();
        above.add(aboveBlock);
        above.add(aboveBlock.getRelative(BlockFace.NORTH));
        above.add(aboveBlock.getRelative(BlockFace.SOUTH));
        above.add(aboveBlock.getRelative(BlockFace.EAST));
        above.add(aboveBlock.getRelative(BlockFace.WEST));
        above.add(aboveBlock.getRelative(BlockFace.NORTH_WEST));
        above.add(aboveBlock.getRelative(BlockFace.NORTH_EAST));
        above.add(aboveBlock.getRelative(BlockFace.SOUTH_WEST));
        above.add(aboveBlock.getRelative(BlockFace.SOUTH_EAST));
        return above;
    }

    public static boolean hasBlocksAt(Player player, int add) {
        for (Block block : getBlocksAt(player, add))
            if (!MaterialUtils.isAir(block.getType()))
                return true;

        return false;
    }

    public static boolean isInBlock(Location location, Material type) {
        for (BlockFace face : BlockFace.values()) {
            if (!face.equals(BlockFace.UP) && !face.equals(BlockFace.DOWN)) {
                Material material = location.getBlock().getRelative(face).getType();
                Block block = location.getBlock().getRelative(face).getLocation().getBlock();

                if (type.equals(Material.WATER))
                    if (block.isLiquid())
                        return true;

                if (type.equals(material))
                    return true;
            }
        }

        return false;
    }

	public static boolean isThroughWall(Location location1, Location location2) {
		double dx = MathUtils.getDistance(location1.getX(), location2.getX());
		double dz = MathUtils.getDistance(location1.getZ(), location2.getZ());
		Location location = null;

		if (dx <= 0.5D && dz >= 1.0D) {
			if (location1.getZ() > location2.getZ())
				location = location1.add(0.0D, 0.0D, -1.0D);

			location = location1.add(0.0D, 0.0D, 1.0D);
		} else if (dz <= 0.5D && dx >= 1.0D) {
			if (location1.getX() > location2.getX())
				location = location1.add(-1.0D, 0.0D, 0.0D);

			location = location1.add(1.0D, 0.0D, 0.0D);
		}

		return location != null && location.getBlock().getType().isSolid() && location.add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid();
	}
	 
	 public static boolean isCritical(Player player) {
		 return (player.getFallDistance() > 0.0F) && (!player.isOnGround()) && (!player.isInsideVehicle()) && (!player.hasPotionEffect(PotionEffectType.BLINDNESS)) && (player.getEyeLocation().getBlock().getType() != Material.LADDER);
	 }
}