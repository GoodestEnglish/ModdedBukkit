package rip.diamond.moddedbukkit.block;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Interface representing a handler for custom blocks.
 */
public interface ModdedBlockHandler {

    /**
     * Creates a new empty block handler.
     *
     * @return An empty block handler
     */
    static ModdedBlockHandler empty() {
        return new ModdedBlockHandler() {};
    }

    /**
     * Handles the block place event.
     *
     * @param event The block place event
     */
    default void onPlace(BlockPlaceEvent event) {};

    /**
     * Handles the block break event.
     *
     * @param event The block break event
     */
    default void onBreak(BlockBreakEvent event) {};

}
