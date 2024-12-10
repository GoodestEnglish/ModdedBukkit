package rip.diamond.moddedbukkit.item;

import rip.diamond.moddedbukkit.ModdedModule;

public interface ModdedItemModule extends ModdedModule {

    /**
     * Register a new custom item.
     *
     * @param item The item which wants to be registered
     */
    void registerItem(ModdedItem item);

    ModdedItem getItem(String key);

}
