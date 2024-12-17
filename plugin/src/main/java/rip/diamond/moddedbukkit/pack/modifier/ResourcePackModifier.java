package rip.diamond.moddedbukkit.pack.modifier;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.pack.ModdedTexture;
import rip.diamond.moddedbukkit.util.FileUtil;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.texture.Texture;

import java.io.File;

@RequiredArgsConstructor
public abstract class ResourcePackModifier {

    protected final ModdedBukkitPlugin plugin;
    protected final ResourcePack pack;

    public abstract void modify();

    public void insertTextureAndModel(ResourcePack pack, ModdedTexture moddedTexture) {
        String name = moddedTexture.getKey().value();
        Key textureKey = moddedTexture.getTextureKey();

        //Extract the png file from plugin resource, and paste it to the ModdedBukkit plugin folder
        FileUtil.extractFile(
                moddedTexture.getTextureResource(),
                new File("plugins/ModdedBukkit/assets/" + name + ".png")
        );

        Texture texture = Texture.texture(Key.key(textureKey.asString() + ".png"), Writable.file(new File("plugins/ModdedBukkit/assets/" + name + ".png")));
        Model model = moddedTexture.createModel();

        pack.texture(texture);
        pack.model(model);
    }

}
