package rip.diamond.moddedbukkit.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rip.diamond.moddedbukkit.ExamplePlugin;
import rip.diamond.moddedbukkit.item.DiamondDirtItem;
import rip.diamond.moddedbukkit.item.ModdedItem;

@RequiredArgsConstructor
public class CustomItemCommand implements CommandExecutor {

    private final ExamplePlugin plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        ModdedItem item = plugin.getItemModule().getItem(DiamondDirtItem.KEY.asString());

        player.getInventory().addItem(item.buildItemStack());

        return false;
    }
}
