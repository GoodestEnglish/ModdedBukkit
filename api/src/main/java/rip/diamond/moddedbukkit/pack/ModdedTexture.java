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

    Key getKey();

    Key getTextureKey();

    InputStream getTextureResource();

    Model createModel();

}
