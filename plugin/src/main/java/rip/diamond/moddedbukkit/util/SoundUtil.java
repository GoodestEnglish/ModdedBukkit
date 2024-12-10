package rip.diamond.moddedbukkit.util;

import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;

public class SoundUtil {

    public static void playSound(Location location, Sound sound) {
        location.getWorld().playSound(location, sound.name().asString(), sound.volume(), sound.pitch());
    }

}
