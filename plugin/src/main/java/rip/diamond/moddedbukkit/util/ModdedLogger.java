package rip.diamond.moddedbukkit.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

@UtilityClass
public class ModdedLogger {

    public static void log(String... str) {
        for (String string : str) {
            Bukkit.getConsoleSender().sendMessage(text("<gray>[<aqua>LOG<gray>] <white>" + string));
        }
    }

    private static Component text(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

}
