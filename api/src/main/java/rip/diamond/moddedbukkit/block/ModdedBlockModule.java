package rip.diamond.moddedbukkit.block;

import org.bukkit.block.Block;
import rip.diamond.moddedbukkit.ModdedModule;

import java.util.Map;

/**
 * Interface representing a module for managing custom blocks.
 * This interface extends ModdedModule and provides methods to register and retrieve custom blocks.
 */
public interface ModdedBlockModule extends ModdedModule {

    /**
     * Registers a new custom block.
     *
     * @param block The block to be registered
     */
    void registerBlock(ModdedBlock block);

    /**
     * Gets a map of all registered custom blocks.
     *
     * @return A map where the key is the block ID and the value is the ModdedBlock
     */
    Map<Integer, ModdedBlock> getBlocks();

    /**
     * Gets a custom block by its ID.
     *
     * @param id The block ID
     * @return The ModdedBlock with the specified ID
     */
    ModdedBlock getBlock(int id);

    /**
     * Gets a custom block by its Bukkit Block instance.
     *
     * @param block The Bukkit Block instance
     * @return The ModdedBlock corresponding to the given Block
     */
    ModdedBlock getBlock(Block block);

    /**
     * Checks if a given Bukkit Block is a custom block.
     *
     * @param block The Bukkit Block instance
     * @return True if the block is a custom block, false otherwise
     */
    boolean isCustomBlock(Block block);

}
