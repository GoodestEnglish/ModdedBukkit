package rip.diamond.moddedbukkit.item;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.List;

public interface ModdedItem {

    NamespacedKey KEY = new NamespacedKey("moddedbukkit", "key");

    Key getKey();

    Component getName();

    @Nullable List<Component> getLore();

    default ItemStack buildItemStack() {
        ItemStack itemStack = ItemStack.of(Material.PAPER);
        ItemMeta meta = itemStack.getItemMeta();

        meta.itemName(getName());
        meta.lore(getLore());
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, getKey().toString());

        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
