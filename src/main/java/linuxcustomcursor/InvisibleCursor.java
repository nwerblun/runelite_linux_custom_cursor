package linuxcustomcursor;

import net.runelite.client.util.ImageUtil;
import java.awt.image.BufferedImage;


public class InvisibleCursor
{
    public static final BufferedImage invisibleCursorImg = ImageUtil.loadImageResource(
            LinuxCustomCursorPlugin.class, "/invisible-cursor.png");
}
