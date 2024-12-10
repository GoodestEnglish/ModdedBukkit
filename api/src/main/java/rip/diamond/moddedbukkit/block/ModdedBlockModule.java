package rip.diamond.moddedbukkit.block;

import org.bukkit.block.Block;
import rip.diamond.moddedbukkit.ModdedModule;

public interface ModdedBlockModule extends ModdedModule {

    /**
     * Register a new custom block.
     *
     * @param block The block which wants to be registered
     */
    void registerBlock(ModdedBlock block);

    ModdedBlock getBlock(int id);

    ModdedBlock getBlock(Block block);

    boolean isCustomBlock(Block block);

}
