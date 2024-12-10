package rip.diamond.moddedbukkit;

/**
 * Represents a modded version of Bukkit with additional functionality provided by modules.
 */
public interface ModdedBukkit {

    <T extends ModdedModule> T initializeModule(Class<T> clazz);

    /**
     * Retrieves a specific module by its class type.
     *
     * @param clazz The class object of the module to retrieve.
     * @return An instance of the requested module, or {@code null} if the module is not available.
     * @param <T> The type of the module being requested.
     */
    <T> T getModule(Class<T> clazz);

}
