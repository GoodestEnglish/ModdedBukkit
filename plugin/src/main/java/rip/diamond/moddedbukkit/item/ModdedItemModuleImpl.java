package rip.diamond.moddedbukkit.item;

import org.bukkit.Bukkit;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.util.ModdedLogger;

import java.util.HashMap;
import java.util.Map;

public class ModdedItemModuleImpl implements ModdedItemModule {

    private final Map<String, ModdedItem> items = new HashMap<>();

    public ModdedItemModuleImpl() {
        Bukkit.getPluginManager().registerEvents(new ModdedItemModuleListener(ModdedBukkitPlugin.INSTANCE, this), ModdedBukkitPlugin.INSTANCE);
    }

    @Override
    public void registerItem(ModdedItem item) {
        String key = item.getKey().asString();

        if (items.containsKey(key)) {
            ModdedLogger.log("Cannot register block '" + key + "' because it is already registered");
            return;
        }

        items.put(key, item);
    }

    @Override
    public ModdedItem getItem(String key) {
        return items.get(key);
    }
}
