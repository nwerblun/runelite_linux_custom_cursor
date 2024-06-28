package linuxcustomcursor;

import java.awt.image.BufferedImage;
import net.runelite.client.util.ImageUtil;


public class InvisibleCursor
{
    public static final BufferedImage invisibleCursorImg = ImageUtil.loadImageResource(
            LinuxCustomCursorPlugin.class, "/invisible-cursor.png");
}
