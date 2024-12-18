package rip.diamond.moddedbukkit.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;

import java.util.Map;

import static rip.diamond.moddedbukkit.pack.modifier.implement.ResourcePackSoundRegistryModifier.*;

@UtilityClass
public class SoundUtil {

    public static Map<Key, Key> REPLACE_TO = Map.of(
            VANILLA_WOOD_PLACE, REPLACED_WOOD_PLACE,
            VANILLA_WOOD_BREAK, REPLACED_WOOD_BREAK,
            VANILLA_WOOD_HIT, REPLACED_WOOD_HIT,
            VANILLA_WOOD_STEP, REPLACED_WOOD_STEP,
            VANILLA_WOOD_FALL, REPLACED_WOOD_FALL,
            VANILLA_STONE_PLACE, REPLACED_STONE_PLACE,
            VANILLA_STONE_BREAK, REPLACED_STONE_BREAK,
            VANILLA_STONE_HIT, REPLACED_STONE_HIT,
            VANILLA_STONE_STEP, REPLACED_STONE_STEP,
            VANILLA_STONE_FALL, REPLACED_STONE_FALL
    );

    public static void playSound(Location location, Sound sound) {
        location.getWorld().playSound(location, sound.name().asString(), sound.volume(), sound.pitch());
    }

    public static void playSound(Location location, Key key, float volume, float pitch) {
        location.getWorld().playSound(location, key.asString(), volume, pitch);
    }

}
