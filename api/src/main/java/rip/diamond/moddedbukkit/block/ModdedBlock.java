package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import rip.diamond.moddedbukkit.pack.ModdedTexture;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;

import java.util.List;
import java.util.Map;

/**
 * Represents a modded block in the game.
 * This interface extends ModdedTexture and provides methods to interact with the block's properties and behaviors.
 */
public interface ModdedBlock extends ModdedTexture {

    /**
     * Gets the unique identifier for this block.
     *
     * @return the block ID
     */
    int getId();

    /**
     * Gets the type of this block.
     *
     * @return the block type
     */
    ModdedBlockType getBlockType();

    /**
     * Gets the hardness of this block.
     *
     * @return the block hardness
     */
    double getHardness();

    /**
     * Gets the sound played when the block is placed.
     *
     * @return the place sound
     */
    Sound getPlaceSound();

    /**
     * Gets the sound played when the block is broken.
     *
     * @return the break sound
     */
    Sound getBreakSound();

    /**
     * Gets the sound played when the block is hit.
     *
     * @return the hit sound
     */
    Sound getHitSound();

    /**
     * Gets the sound played when the block is stepped on.
     *
     * @return the step sound
     */
    Sound getStepSound();

    /**
     * Gets the sound played when the block falls.
     *
     * @return the fall sound
     */
    Sound getFallSound();

    /**
     * Gets the list of items dropped when the block is broken.
     *
     * @param tool the tool used to break the block
     * @return the list of dropped items
     */
    List<ItemStack> getDrops(@Nullable ItemStack tool);

    /**
     * Gets the handler for this block.
     *
     * @return the block handler
     */
    ModdedBlockHandler getHandler();

    /**
     * Gets the Bukkit-implemented block data.
     *
     * @return the block data
     */
    default BlockData getBukkitBlockData() {
        return ModdedBlockData.toBlockData(getBlockType(), getId());
    }

    /**
     * Creates the model representation of this block.
     * This is used to generate the model for the resource pack.
     *
     * @return the model
     */
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
