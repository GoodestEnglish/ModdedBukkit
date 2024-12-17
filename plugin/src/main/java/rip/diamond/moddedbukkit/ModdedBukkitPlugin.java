package rip.diamond.moddedbukkit;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import rip.diamond.moddedbukkit.pack.ResourcePackManager;
import rip.diamond.moddedbukkit.block.ModdedBlockModulePacketListener;
import rip.diamond.moddedbukkit.util.ModdedLogger;

import java.util.HashMap;
import java.util.Map;

public class ModdedBukkitPlugin extends JavaPlugin implements ModdedBukkit {

    public static ModdedBukkitPlugin INSTANCE;

    private final Map<Class<?>, ModdedModule> modules = new HashMap<>();

    @Getter private ResourcePackManager resourcePackManager;

    @Override
    public void onLoad() {
        INSTANCE = this;

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(ModdedBukkitPlugin.INSTANCE));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        resourcePackManager = new ResourcePackManager(this);

        PacketEvents.getAPI().init();
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    @Override
    @SneakyThrows
    public <T extends ModdedModule> T initializeModule(Class<T> clazz) {
        ModdedModule module = clazz.getDeclaredConstructor(ModdedBukkitPlugin.class).newInstance(this);

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
