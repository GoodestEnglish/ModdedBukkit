package rip.diamond.moddedbukkit.item;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Range;
import rip.diamond.moddedbukkit.pack.ModdedTexture;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Interface representing a custom item.
 */
public interface ModdedItem extends ModdedTexture {

    NamespacedKey KEY = new NamespacedKey("moddedbukkit", "key");

    /**
     * Gets the ID of this custom item.
     * <p>
     * ID is mostly used in creating paper's custom model data.
     *
     * @return The ID
     */
    @Range(from = 1, to = Integer.MAX_VALUE) int getId();

    /**
     * Gets the name of this custom item.
     *
     * @return The name as a Component
     */
    Component getName();

    /**
     * Gets the lore of this custom item.
     *
     * @return The lore as a list of Components, or null if there is no lore
     */
    @Nullable List<Component> getLore();

    /**
     * Builds an ItemStack representation of this custom item.
     *
     * @return The ItemStack
     */
    default ItemStack buildItemStack() {
        ItemStack itemStack = ItemStack.of(Material.PAPER);
        ItemMeta meta = itemStack.getItemMeta();

        meta.itemName(getName());
        meta.lore(getLore());
        meta.setCustomModelData(getId());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getKey().toString());

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Creates the model representation of this ModdedItem.
     * This is used to generate the model for the resource pack.
     *
     * @return The model
     */
    @Override
    default Model createModel() {
        Key textureKey = getTextureKey();

        return Model.model()
                .parent(Key.key("minecraft:item/generated"))
                .key(textureKey)
                .textures(ModelTextures.builder()
                        .layers(
                                ModelTexture.ofKey(textureKey)
                        )
                        .build()
                )
                .build();
    }

}
