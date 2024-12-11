package rip.diamond.moddedbukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Switch;

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

}
