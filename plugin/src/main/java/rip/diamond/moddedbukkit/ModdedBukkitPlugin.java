package rip.diamond.moddedbukkit;

import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import rip.diamond.moddedbukkit.util.ModdedLogger;

import java.util.HashMap;
import java.util.Map;

public class ModdedBukkitPlugin extends JavaPlugin implements ModdedBukkit {

    public static ModdedBukkitPlugin INSTANCE;

    private final Map<Class<?>, ModdedModule> modules = new HashMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
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
