package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.block.data.BlockData;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;

import java.io.InputStream;
import java.util.Map;

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

    default Model createModel() {
        Key textureKey = getTextureKey();

        return Model.model()
                .parent(Key.key("minecraft:block/cube_all"))
                .key(textureKey)
                .textures(ModelTextures.builder()
                        .variables(Map.of("all", ModelTexture.ofKey(textureKey)))
                        .build()
                )
                .build();
    }
}
