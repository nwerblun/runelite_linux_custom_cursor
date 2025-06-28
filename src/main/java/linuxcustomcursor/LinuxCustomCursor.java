package linuxcustomcursor;

import lombok.Getter;
import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import net.runelite.client.plugins.customcursor.CustomCursorPlugin;
import net.runelite.client.util.ImageUtil;


@Getter
public enum LinuxCustomCursor
{
    NONE("None"),
    RS3_GOLD("RS3 Gold", "cursor-rs3-gold.png", false),
    RS3_SILVER("RS3 Silver", "cursor-rs3-silver.png", false),
    DRAGON_DAGGER("Dragon Dagger", "cursor-dragon-dagger.png", false),
    DRAGON_DAGGER_POISON("Dragon Dagger (p)", "cursor-dragon-dagger-p.png", false),
    TROUT("Trout", "cursor-trout.png", false),
    // D scim icon loading in incorrectly, provided new one from the wiki.
    DRAGON_SCIMITAR("Dragon Scimitar", "cursor-dragon-scimitar.png", true),
    ABYSSAL_DAGGER("Abyssal Dagger (p)", "cursor-abyssal-dagger-p.png", true),
    BLADE_OF_SAELDOR("Blade of Saeldor (c)", "cursor-blade-of-saeldor.png", true),
    BRUMA_TORCH("Bruma Torch", "cursor-bruma-torch.png", true),
    COLOSSAL_BLADE("Colossal Blade", "cursor-colossal-blade.png", true),
    DRAGON_HUNTER_LANCE("Dragon Hunter Lance", "cursor-dragon-hunter-lance.png", true),
    EMBERLIGHT("Emberlight", "cursor-emberlight.png", true),
    GILDED_SCIMITAR("Gilded Scimitar", "cursor-gilded-scimitar.png", true),
    GILDED_SPADE("Gilded Spade", "cursor-gilded-spade.png", true),
    HAM_JOINT("Ham Joint", "cursor-ham-joint.png", true),
    KERIS_PARTISAN("Keris Partisan", "cursor-keris-partisan.png", true),
    OSMUMTENS_FANG("Osmumten's Fang", "cursor-osmumtens-fang.png", true),
    THUNDER_KHOPESH("Thunder Khopesh", "cursor-thunder-khopesh.png", true),
    TOKTZ_XIL_EK("Toktz-Xil-Ek", "cursor-toktz-xil-ek.png", true),
    VOIDWAKER("Voidwaker", "cursor-voidwaker.png", true),
    SALMON("Salmon", "cursor-salmon.png", true),
    TECU_SALAMANDER("Tecu Salamander", "cursor-tecu-salamander.png", true),
    DRAGON_DEFENDER("Dragon Defender", "cursor-dragon-defender.png", true),
    ARMADYL_GODSWORD("Armadyl Godsword", "cursor-ags.png", true),
    TWENTY_FOUR_CARAT_SWORD("24-Carat Sword", "cursor-24-carat-sword.png", true),
    CURSED_BANANA("Cursed Banana", "cursor-cursed-banana.png", true),
    DRAGON_HALBERD("Dragon Halberd", "cursor-dragon-halberd.png", true),
    HUNTING_KNIFE("Hunting Knife", "cursor-hunting-knife.png", true),
    LOBSTER("Lobster", "cursor-lobster.png", true),
    RED_CHINCHOMPA("Red Chinchompa", "cursor-red-chinchompa.png", true),
    RUBBER_CHICKEN("Rubber Chicken", "cursor-rubber-chicken.png", true),
    STALE_BAGUETTE("Stale Baguette", "cursor-stale-baguette.png", true),
    EQUIPPED_WEAPON("Equipped Weapon"),
    CUSTOM_IMAGE("Custom Image");

    private final String name;
    @Nullable
    private final BufferedImage cursorImage;
    private final boolean notInOriginal;

    LinuxCustomCursor(String name)
    {
        this.name = name;
        this.cursorImage = null;
        this.notInOriginal = false;
    }


    LinuxCustomCursor(String name, String icon, boolean notInOriginal)
    {
        this.name = name;
        this.notInOriginal = notInOriginal;
        // '/' is needed in the image path or it won't be able to find the resources.
        if (notInOriginal) this.cursorImage = ImageUtil.loadImageResource(LinuxCustomCursorPlugin.class, "/" + icon);
        else this.cursorImage = ImageUtil.loadImageResource(CustomCursorPlugin.class, icon);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
