package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import rip.diamond.moddedbukkit.pack.ModdedTexture;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;

import java.util.List;
import java.util.Map;

public interface ModdedBlock extends ModdedTexture {

    int getId();

    ModdedBlockType getBlockType();

    double getHardness();

    Sound getPlaceSound();

    Sound getBreakSound();

    List<ItemStack> getDrops(ItemStack tool);

    ModdedBlockHandler getHandler();

    /**
     * Get the bukkit-implemented block data.
     *
     * @return The block data
     */
    default BlockData getBukkitBlockData() {
        return ModdedBlockData.toBlockData(getBlockType(), getId());
    }

    @Override
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
