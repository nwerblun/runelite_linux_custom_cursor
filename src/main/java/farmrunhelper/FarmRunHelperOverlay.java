package farmrunhelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class FarmRunHelperOverlay extends Overlay
{
    private final Client client;
    private final FarmRunHelperConfig config;
    private final FarmRunHelperPlugin plugin;

    @Inject
    private FarmRunHelperOverlay(Client client, FarmRunHelperConfig config, FarmRunHelperPlugin plugin)
    {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        return null;
    }

}
