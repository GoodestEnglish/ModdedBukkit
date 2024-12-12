package rip.diamond.moddedbukkit.item;

import net.kyori.adventure.key.Key;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;

import java.util.Map;

public interface ModdedPlaceableItem extends ModdedItem {

    int getBlockId();

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
