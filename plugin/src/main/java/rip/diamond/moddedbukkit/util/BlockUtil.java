package rip.diamond.moddedbukkit.util;

import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Switch;

public class BlockUtil {

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
