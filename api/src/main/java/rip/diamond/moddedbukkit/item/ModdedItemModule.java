package rip.diamond.moddedbukkit.item;

import rip.diamond.moddedbukkit.ModdedModule;

import java.util.Map;

public interface ModdedItemModule extends ModdedModule {

    /**
     * Register a new custom item.
     *
     * @param item The item which wants to be registered
     */
    void registerItem(ModdedItem item);

    Map<String, ModdedItem> getItems();

    ModdedItem getItem(String key);

}
