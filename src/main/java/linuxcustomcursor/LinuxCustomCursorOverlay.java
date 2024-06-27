package linuxcustomcursor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayUtil;

@Slf4j
class LinuxCustomCursorOverlay extends Overlay
{
    private final Client client;
    private final LinuxCustomCursorConfig config;
    private final LinuxCustomCursorPlugin plugin;

    @Setter
    private BufferedImage cursorImg;

    @Inject
    private LinuxCustomCursorOverlay(Client client, LinuxCustomCursorConfig config, LinuxCustomCursorPlugin plugin)
    {
        super(plugin);
        setPriority(Overlay.PRIORITY_HIGHEST);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.client = client;
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (cursorImg != null && client.getMouseCanvasPosition() != null)
        {
            Point adjustedLoc = new Point(client.getMouseCanvasPosition().getX(),
                    client.getMouseCanvasPosition().getY() - LinuxCustomCursorPlugin.CURSOR_HEIGHT/2);
            OverlayUtil.renderImageLocation(graphics, adjustedLoc, cursorImg);
        }
        return null;
    }
}
