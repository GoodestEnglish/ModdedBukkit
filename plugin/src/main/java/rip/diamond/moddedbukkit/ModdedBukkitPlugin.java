package rip.diamond.moddedbukkit;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import rip.diamond.moddedbukkit.pack.ResourcePackManager;
import rip.diamond.moddedbukkit.util.ModdedLogger;

import java.util.HashMap;
import java.util.Map;

public class ModdedBukkitPlugin extends JavaPlugin implements ModdedBukkit {

    public static ModdedBukkitPlugin INSTANCE;

    private final Map<Class<?>, ModdedModule> modules = new HashMap<>();

    @Getter private ResourcePackManager resourcePackManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        resourcePackManager = new ResourcePackManager(this);
    }

    @Override
    @SneakyThrows
    public <T extends ModdedModule> T initializeModule(Class<T> clazz) {
        ModdedModule module = clazz.getDeclaredConstructor().newInstance();

        modules.put(clazz, module);
        ModdedLogger.log("Initialized module " + clazz.getSimpleName());

        return clazz.cast(module);
    }

    @Override
    public <T> T getModule(Class<T> clazz) {
        ModdedModule module = modules.get(clazz);

        if (module == null) {
            return null;
        }

        return clazz.cast(module);
    }

}
