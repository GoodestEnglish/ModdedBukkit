package rip.diamond.moddedbukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

@UtilityClass
public class BlockUtil {

    /**
     * Helper function to check if the block is interactable.
     * <p>
     * Interactable blocks means block which right click without sneaking will perform some actions,
     * and prevent block placing.
     *
     * @param block The block which wants to be checked
     * @return true if the block is interactable, false otherwise
     */
    public static boolean isInteractable(Block block) {
        BlockData blockData = block.getBlockData();
        BlockState blockState = block.getState();
        Material material = block.getType();

        return blockData instanceof Openable || //Door, Trapdoor, etc.
                blockData instanceof Switch || //Lever, Button
                blockState instanceof TileState || //Chest, Furnace, Enchanting Table, Skull, etc.
                material == Material.CRAFTING_TABLE || material == Material.ANVIL //Others which cannot be determined by BlockData and BlockState
        ;
    }

    //TODO: Recode this
    //Credit: Oraxen - BlockHelpers
    public static Block getBlockStandingOn(Entity entity) {
        Block block = entity.getLocation().getBlock();
        Block blockBelow = block.getRelative(BlockFace.DOWN);
        if (!block.getType().isAir() && block.getType() != Material.LIGHT) return block;
        if (!blockBelow.getType().isAir()) return blockBelow;

        // Expand players hitbox by 0.3, which is the maximum size a player can be off a block
        // Whilst not falling off
        BoundingBox entityBox = entity.getBoundingBox().expand(0.3);
        for (BlockFace face : BlockFace.values()) {
            if (!face.isCartesian() || face.getModY() != 0) continue;
            Block relative = blockBelow.getRelative(face);
            if (relative.getType() == Material.AIR) continue;
            if (relative.getBoundingBox().overlaps(entityBox)) return relative;
        }

        return null;
    }

}
