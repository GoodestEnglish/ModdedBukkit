package rip.diamond.moddedbukkit.block;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public interface ModdedBlockHandler {

    /**
     * Create a new empty block handler.
     *
     * @return An empty block handler
     */
    static ModdedBlockHandler empty() {
        return new ModdedBlockHandler() {};
    }

    default void onPlace(BlockPlaceEvent event) {};

    default void onBreak(BlockBreakEvent event) {};

}
