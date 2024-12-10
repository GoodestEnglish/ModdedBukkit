package rip.diamond.moddedbukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import rip.diamond.moddedbukkit.block.DiamondDirtBlock;
import rip.diamond.moddedbukkit.block.ModdedBlockModule;
import rip.diamond.moddedbukkit.block.ModdedBlockModuleImpl;
import rip.diamond.moddedbukkit.command.CustomItemCommand;
import rip.diamond.moddedbukkit.item.DiamondDirtItem;
import rip.diamond.moddedbukkit.item.ModdedItemModule;
import rip.diamond.moddedbukkit.item.ModdedItemModuleImpl;

@Getter
public class ExamplePlugin extends JavaPlugin {

    public static ExamplePlugin INSTANCE;

    private ModdedItemModule itemModule;
    private ModdedBlockModule blockModule;

    @Override
    public void onEnable() {
        INSTANCE = this;

        //Initialize modules we want to use
        itemModule = ModdedBukkitPlugin.INSTANCE.initializeModule(ModdedItemModuleImpl.class);
        blockModule = ModdedBukkitPlugin.INSTANCE.initializeModule(ModdedBlockModuleImpl.class);

        //Register custom item
        itemModule.registerItem(new DiamondDirtItem());

        //Register custom block
        blockModule.registerBlock(new DiamondDirtBlock());

        //Register a command to give custom item
        getServer().getPluginCommand("customitem").setExecutor(new CustomItemCommand(this));
    }
}
