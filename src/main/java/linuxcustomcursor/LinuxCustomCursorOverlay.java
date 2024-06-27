package linuxcustomcursor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class LinuxCustomCursorOverlay extends OverlayPanel
{
    private final Client client;
    private final LinuxCustomCursorConfig config;
    private final LinuxCustomCursorPlugin plugin;

    @Inject
    private LinuxCustomCursorOverlay(Client client, LinuxCustomCursorConfig config, LinuxCustomCursorPlugin plugin)
    {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.client = client;
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.isCurrentlyActive())
        {
            return null;
        }
        // TODO: Add logic to check if the required stuff is in inv/equipped
        // If not, add an else to this that renders it as red
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Getting Materials")
                .color(Color.RED)
                .build()
        );

        panelComponent.getChildren().add(LineComponent.builder()
                .left("hoes")
                .right("none at all")
                .build()
        );

        if (config.enableTreeRun())
        {
            if (plugin.playerHasEnoughTreeSaplings())
            {
                panelComponent.getChildren().add(LineComponent.builder()
                        .left("Tree Saplings")
                        .right("none at all")
                        .build()
                );
            }
            else
            {

            }
        }

        return super.render(graphics);
    }
}
