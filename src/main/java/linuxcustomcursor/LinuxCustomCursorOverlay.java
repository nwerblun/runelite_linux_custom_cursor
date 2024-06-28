package linuxcustomcursor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ClientUI;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayUtil;

@Slf4j
class LinuxCustomCursorOverlay extends Overlay
{
    private final Client client;
    private final LinuxCustomCursorConfig config;
    private final LinuxCustomCursorPlugin plugin;
    private final ClientUI clientUI;
    private final ClientThread clientThread;
    private final ItemManager itemManager;

    @Setter
    private boolean disableOverlay;

    @Inject
    private LinuxCustomCursorOverlay(Client client, LinuxCustomCursorConfig config,
                                     LinuxCustomCursorPlugin plugin, ClientUI clientUI,
                                     ClientThread clientThread, ItemManager itemManager)
    {
        super(plugin);
        setPriority(Overlay.PRIORITY_HIGHEST);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        this.clientUI = clientUI;
        this.clientThread = clientThread;
        this.itemManager = itemManager;
        disableOverlay = false;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        BufferedImage cursorImg = updateCursorImg();
        Point mouseLoc = client.getMouseCanvasPosition();
        if (disableOverlay || mouseLoc == null || !mouseInsideBounds(mouseLoc) || cursorImg == null)
        {

            clientUI.resetCursor();
            return null;
        }

        OverlayUtil.renderImageLocation(graphics, getAdjustedMousePoint(mouseLoc), cursorImg);

        if (!config.debugEnableDrawSystemCursor())
        {
            clientUI.setCursor(InvisibleCursor.invisibleCursorImg, "Invisible Cursor");
        }
        else
        {
            clientUI.resetCursor();
        }
        return null;
    }

    private BufferedImage updateCursorImg()
    {
        LinuxCustomCursor selectedCursor = config.selectedCursor();
        if (selectedCursor.getCursorImage() != null)
        {
            return selectedCursor.getCursorImage();
        }
        else if (selectedCursor == LinuxCustomCursor.CUSTOM_IMAGE)
        {
            try
            {
                File customCursorFile = new File(RuneLite.RUNELITE_DIR, "cursor.png");
                if (customCursorFile.exists())
                {
                    BufferedImage img;
                    synchronized (ImageIO.class)
                    {
                        img = ImageIO.read(customCursorFile);
                    }
                    return img;
                }
            }
            catch (Exception e)
            {
                log.error("Loading custom image file failed", e);
                return null;
            }
        }
        else if (selectedCursor == LinuxCustomCursor.EQUIPPED_WEAPON)
        {
            ItemContainer playerEquipment = client.getItemContainer(InventoryID.EQUIPMENT);
            if (playerEquipment == null) {
                return null;
            }

            Item equippedWeapon = playerEquipment.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());
            // should short circuit if null
            if (equippedWeapon == null && equippedWeapon.getQuantity() <= 0)
            {
                return null;
            }

            return itemManager.getImage(equippedWeapon.getId());
        }
        // if NONE then all above will be false, and we return null anyway
        return null;
    }

    private boolean mouseInsideBounds(Point mouseLoc)
    {
        return mouseLoc.getX() > 0 && mouseLoc.getX() <= client.getCanvasWidth() &&
                mouseLoc.getY() > 0 && mouseLoc.getY() <= client.getCanvasHeight();
    }


    private Point getAdjustedMousePoint(Point mouseLoc)
    {
        // align the top left of the image to the hotspot of the cursor
        // the draw point seems to have the left edge of the image aligned with the center of the cursor box
        // the y value seems to be at the very bottom of the cursor
        int newX = mouseLoc.getX() - config.getSystemCursorWidth()/2 + config.getSystemCursorHotSpotXOffset();
        if (newX < 0) newX = 0;
        if (newX > client.getCanvasWidth()) newX = client.getCanvasWidth();

        int newY = mouseLoc.getY() - config.getSystemCursorHeight() + config.getSystemCursorHotSpotYOffset();
        if (newY < 0) newY = 0;
        if (newY > client.getCanvasHeight()) newY = client.getCanvasHeight();

        return new Point(newX, newY);
    }
}
