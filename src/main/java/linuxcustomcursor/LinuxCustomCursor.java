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
    DRAGON_SCIMITAR("Dragon Scimitar", "cursor-dragon-scimitar.png", false),
//    SALMON("Salmon", "cursor-salmon.png", true),
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
        if (notInOriginal) this.cursorImage = ImageUtil.loadImageResource(LinuxCustomCursorPlugin.class, icon);
        else this.cursorImage = ImageUtil.loadImageResource(CustomCursorPlugin.class, icon);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
