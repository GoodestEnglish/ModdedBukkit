package rip.diamond.moddedbukkit.item;

import net.kyori.adventure.key.Key;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;

import java.util.Map;

/**
 * Interface representing a custom placeable item.
 */
public interface ModdedPlaceableItem extends ModdedItem {

    /**
     * Gets the custom block ID associated with this placeable item.
     *
     * @return The block ID
     */
    int getBlockId();

    /**
     * Creates the model representation of this ModdedPlaceableItem.
     * This method overrides the default implementation to use a block model.
     *
     * @return The model
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
