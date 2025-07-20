package linuxcustomcursor;

import java.awt.image.BufferedImage;
import net.runelite.client.util.ImageUtil;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Cursor;


public class InvisibleCursor
{
    public static final BufferedImage invisibleCursorImg = ImageUtil.loadImageResource(
            LinuxCustomCursorPlugin.class, "/invisible-cursor.png");
    private static final Point hotSpot = new Point(0, 0);
    public static final Cursor invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            invisibleCursorImg, hotSpot, "Invisible Cursor");
}
