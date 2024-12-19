package rip.diamond.moddedbukkit.item;

import rip.diamond.moddedbukkit.ModdedModule;

import java.util.Map;

/**
 * Interface representing a module for managing modded items.
 */
public interface ModdedItemModule extends ModdedModule {

    /**
     * Registers a new custom item.
     *
     * @param item The item to be registered
     */
    void registerItem(ModdedItem item);

    /**
     * Gets a map of all registered custom items.
     *
     * @return A map where the key is the item key and the value is the ModdedItem
     */
    Map<String, ModdedItem> getItems();

    /**
     * Gets a custom item by its key.
     *
     * @param key The item key
     * @return The ModdedItem with the specified key
     */
    ModdedItem getItem(String key);

}
