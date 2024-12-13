package rip.diamond.moddedbukkit.pack;

import net.kyori.adventure.key.Key;
import rip.diamond.moddedbukkit.block.ModdedBlock;
import rip.diamond.moddedbukkit.item.ModdedItem;
import team.unnamed.creative.model.Model;

import java.io.InputStream;

/**
 * Represent something which needs to be rendered by using resource pack.
 * For example, {@link ModdedItem} and {@link ModdedBlock}
 */
public interface ModdedTexture {

    /**
     * Get the unique key for this object.
     * This is mostly used to identify the name for this object.
     *
     * @return The unique key
     */
    Key getKey();

    /**
     * Get the texture key for this object.
     * Texture key is used for locate where should the texture goes in the resource pack.
     *
     * @return The texture key
     */
    Key getTextureKey();

    /**
     * Get the path of the texture image file, which located inside the plugin.
     * The InputStream is used to extract the texture image file to the plugin folder.
     *
     * @return The InputStream
     */
    InputStream getTextureResource();

    /**
     * Create the Creative Model class, which represent the model json file.
     *
     * @return The Model
     */
    Model createModel();

}
