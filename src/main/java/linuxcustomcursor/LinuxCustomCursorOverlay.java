package linuxcustomcursor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.client.RuneLite;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ClientUI;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

@Slf4j
class LinuxCustomCursorOverlay extends Overlay
{
    private final Client client;
    private final LinuxCustomCursorConfig config;
    private final LinuxCustomCursorPlugin plugin;
    private final ClientUI clientUI;
    private final ItemManager itemManager;
    private BufferedImage cachedCustomImage;
    private long customImageLastModified = 0;
    private BufferedImage cachedWeaponImage;
    private int cachedWeaponId = -1;
    private LinuxCustomCursor lastSelectedCursor;

    @Setter
    private boolean disableOverlay;

    @Inject
    private LinuxCustomCursorOverlay(Client client, LinuxCustomCursorConfig config,
                                     LinuxCustomCursorPlugin plugin, ClientUI clientUI, ItemManager itemManager)
    {
        super(plugin);
        setPriority(Overlay.PRIORITY_HIGHEST);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        setMovable(false);
        setResettable(false);
        setResizable(false);
        setSnappable(false);
        setPreferredPosition(OverlayPosition.DYNAMIC);
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        this.clientUI = clientUI;
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
        
        if (lastSelectedCursor != selectedCursor)
        {
            clearCacheForCursorChange(lastSelectedCursor, selectedCursor);
            lastSelectedCursor = selectedCursor;
        }
        
        if (selectedCursor.getCursorImage() != null)
        {
            return selectedCursor.getCursorImage();
        }
        else if (selectedCursor == LinuxCustomCursor.CUSTOM_IMAGE)
        {
            return getCachedCustomImage();
        }
        else if (selectedCursor == LinuxCustomCursor.EQUIPPED_WEAPON)
        {
            return getCachedWeaponImage();
        }
        // if NONE then all above will be false, and we return null anyway
        return null;
    }
    
    private BufferedImage getCachedCustomImage()
    {
        try
        {
            File customCursorFile = new File(RuneLite.RUNELITE_DIR, "cursor.png");
            
            // file doesn't exist, clear cache if needed
            if (!customCursorFile.exists())
            {
                if (cachedCustomImage != null)
                {
                    cachedCustomImage.flush();
                    cachedCustomImage = null;
                    customImageLastModified = 0;
                }
                
                return null;
            }
            
            // check cursor last modified time to automatically reload on change
            long fileLastModified = customCursorFile.lastModified();
            
            // check if the image needs to be reloaded
            if (cachedCustomImage == null || fileLastModified != customImageLastModified)
            {
                // dispose of old image
                if (cachedCustomImage != null)
                {
                    cachedCustomImage.flush();
                }
                
                // load new image
                synchronized (ImageIO.class)
                {
                    cachedCustomImage = ImageIO.read(customCursorFile);
                }
                
                customImageLastModified = fileLastModified;
                log.debug("Loaded custom cursor image from file");
            }
            
            return cachedCustomImage;
        }
        catch (Exception e)
        {
            log.error("Loading custom image file failed", e);
            
            // clear cache on error
            if (cachedCustomImage != null)
            {
                cachedCustomImage.flush();
                cachedCustomImage = null;
                customImageLastModified = 0;
            }
            
            return null;
        }
    }
    
    private BufferedImage getCachedWeaponImage()
    {
        ItemContainer playerEquipment = client.getItemContainer(InventoryID.EQUIPMENT);
        if (playerEquipment == null)
        {
            clearWeaponCache();
            return null;
        }
        
        Item equippedWeapon = playerEquipment.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());
        
        if (equippedWeapon == null || equippedWeapon.getQuantity() <= 0)
        {
            clearWeaponCache();
            return null;
        }
        
        int weaponId = equippedWeapon.getId();
        
        // check if the image needs to be reloaded
        if (cachedWeaponImage == null || cachedWeaponId != weaponId)
        {
            // dispose of old image
            if (cachedWeaponImage != null)
            {
                cachedWeaponImage.flush();
            }
            
            cachedWeaponImage = itemManager.getImage(weaponId);
            cachedWeaponId = weaponId;
            log.debug("Cached weapon image for item ID: {}", weaponId);
        }
        
        return cachedWeaponImage;
    }
    
    private void clearWeaponCache()
    {
        if (cachedWeaponImage != null)
        {
            cachedWeaponImage.flush();
            cachedWeaponImage = null;
            cachedWeaponId = -1;
        }
    }
    
    private void clearCacheForCursorChange(LinuxCustomCursor oldCursor, LinuxCustomCursor newCursor)
    {
        // clear custom image cache if switching images
        if (oldCursor == LinuxCustomCursor.CUSTOM_IMAGE && newCursor != LinuxCustomCursor.CUSTOM_IMAGE)
        {
            if (cachedCustomImage != null)
            {
                cachedCustomImage.flush();
                cachedCustomImage = null;
                customImageLastModified = 0;
                log.debug("Cleared custom image cache on cursor change");
            }
        }
        
        // clear weapon image cache if switching weapon
        if (oldCursor == LinuxCustomCursor.EQUIPPED_WEAPON && newCursor != LinuxCustomCursor.EQUIPPED_WEAPON)
        {
            clearWeaponCache();
            log.debug("Cleared weapon image cache on cursor change");
        }
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
