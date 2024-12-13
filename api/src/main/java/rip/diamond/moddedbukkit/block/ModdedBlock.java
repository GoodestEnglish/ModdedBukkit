package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.block.data.BlockData;

import java.io.InputStream;

public interface ModdedBlock {

    Key getKey();

    Key getTextureKey();

    InputStream getTextureResource();

    int getId();

    ModdedBlockType getBlockType();

    Sound getPlaceSound();

    Sound getBreakSound();

    ModdedBlockHandler getHandler();

    /**
     * Get the bukkit-implemented block data.
     *
     * @return The block data
     */
    default BlockData getBukkitBlockData() {
        return ModdedBlockData.toBlockData(getBlockType(), getId());
    }

}
