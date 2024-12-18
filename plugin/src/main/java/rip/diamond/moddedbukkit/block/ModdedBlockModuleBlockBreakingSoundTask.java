package rip.diamond.moddedbukkit.block;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import rip.diamond.moddedbukkit.util.SoundUtil;

@RequiredArgsConstructor
public class ModdedBlockModuleBlockBreakingSoundTask extends BukkitRunnable {

    private final Location location;
    private final Sound sound;

    @Override
    public void run() {
        SoundUtil.playSound(location, sound);
    }

}
